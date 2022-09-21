@file:Suppress("NAME_SHADOWING")

package com.example.frisbeeapp.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.frisbeeapp.R
import com.example.frisbeeapp.viewmodel.CourseViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import com.google.android.gms.tasks.CancellationTokenSource


class MapsFragment : Fragment(R.layout.fragment_maps),
    EasyPermissions.PermissionCallbacks {

    companion object {
        const val PERMISSION_LOCATION_REQUEST_CODE = 1
    }
    // default lokasjon hvis bruker ikke oensker aa gi tillatelse til egen posisjon
    private val defaultLocation = LatLng(59.911491, 10.757933)

    // delt viewmodel for aa faa tak i course-objektene
    private val model: CourseViewModel by activityViewModels()
    // brukes til aa hente egen lokasjon
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    // token til getCurrentLocation
    private val cancellationTokenSource =  CancellationTokenSource()

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        /*
           Callbacket blir trigget når kartet er klart til bruk.
           Her legges markers og egen posisjon til.
         */

        model.getCourses().observe(viewLifecycleOwner) { course ->
            course.forEach { course ->
                googleMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            course.latitude!!,
                            course.longitude!!
                        )
                    ).title(course.name
                    ).icon(bitmapFromVector(this.requireContext(), R.drawable.ic_kurvmarker)))

            }
        }
        // setter padding for at zoom-knapper skal bli synlige pga nav bar
        googleMap.setPadding(0,0,0,200)
        googleMap.uiSettings.isZoomControlsEnabled = true

        // sjekker lokasjons-rettigheter og setter kamera til egen lokasjon
        if(hasLocationPermission()) {
            fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token).addOnCompleteListener {
                val currentlocation = it.result
                if (currentlocation != null) {
                    val currentlatlng = LatLng(currentlocation.latitude, currentlocation.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentlatlng))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10F))

                    googleMap.isMyLocationEnabled = true
                }
            }
        }
        // default location hvis ikke rettigheter om egen lokasjon - satt til Oslo
        else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10F))
            googleMap.isMyLocationEnabled = false
        }
    }
    // initialiserer kartfragmentet og kaller paa callbacket asynkront
    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        super.onViewCreated(view, savedInstanceState)
    }

    // spoerr om rettigheter og initialiserer fused location provider client for egen lokasjon
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLocationPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    // sjekker om bruker har lokasjons
    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    @Suppress("DEPRECATION")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "Appen vil ikke fungere optimalt uten tillatelse",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            requireContext(),
            "Tillatelse tilatt",
            Toast.LENGTH_SHORT
        ).show()
    }

    // setter bilde paa markers til selv-laget marker
    private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        // below line is use to generate a drawable.
        val vectorDrawable: Drawable? = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
