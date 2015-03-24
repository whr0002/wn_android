package com.map.woodlands.woodlandsmap.Data.SAXKML;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.map.woodlands.woodlandsmap.Data.Coordinate;
import com.map.woodlands.woodlandsmap.Data.KMLController;
import com.map.woodlands.woodlandsmap.Data.MarkerToggler;
import com.map.woodlands.woodlandsmap.Data.ViewToggler;
import com.map.woodlands.woodlandsmap.R;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy on 3/20/2015.
 */
public class MapController {
    private GoogleMap map;
    private AsyncHttpClient client;
    private CoordinatesParser coordinatesParser;
    private MarkerToggler mt;
    ArrayList<Marker> markers;
    private ViewToggler viewToggler;
    private KMLController kmlController;

    public MapController(GoogleMap gmap, MarkerToggler m, ViewToggler viewToggler){
        this.map = gmap;
        this.client = new AsyncHttpClient();
        this.coordinatesParser = new CoordinatesParser();
        this.mt = m;
        this.markers = new ArrayList<Marker>();
        this.viewToggler = viewToggler;
        this.kmlController = new KMLController();
    }

    public void loadKML(final String url, final String fileName){
        viewToggler.toggleLoadingView();
        NavigationDataSet navigationDataSet = null;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String filetype = url.substring(url.length()-3);
                kmlController.saveFile(bytes, fileName+"."+filetype);
                Log.i("debug", "Got KML file");
                if(url.contains("kml")) {
                    // It is a kml file
                    addDataToMap(MapService.getNavigationDataSet(bytes));
                }else if(url.contains("kmz")){
                    // It is a kmz file
                    addDataToMap(MapService.getNavigationDataSet(kmlController.getKMLFromKMZ(bytes)));

                }
                viewToggler.toggleLoadingView();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i("debug", "Fail to get KML file");
                viewToggler.toggleLoadingView();
            }
        });


    }

    public void addDataToMap(NavigationDataSet n){
        if(n != null) {
            ArrayList<Placemark> placemarks = n.getPlacemarks();
            ArrayList<Marker> markers = new ArrayList<Marker>();
            ArrayList<Polyline> polylines = new ArrayList<Polyline>();
            for (Placemark p : placemarks) {
                ArrayList<LatLng> latLngs = new ArrayList<LatLng>();

                latLngs = coordinatesParser.getLatLngs(p);

                if (latLngs.size() > 0) {
                    if (p.getType() != null) {
                        if (p.getType().equals("Point")) {
                            // This is Point placemark

                            Marker m = map.addMarker(new MarkerOptions().position(latLngs.get(0)).title(p.title));
                            markers.add(m);

                        } else if (p.getType().equals("Polygon")) {
                            // This is Polygon placemark

                            Iterable<LatLng> iterable = latLngs;
                            Polyline polyline = map.addPolyline(new PolylineOptions().addAll(iterable).width(5).color(R.color.lightBlue));
                            polylines.add(polyline);

                        }
                    }
                }
            }
            if (markers.size() > 0) {
                autocenterPoint(markers);
            } else if (polylines.size() > 0) {
                autocenterPolygon(polylines);
            }
        }

    }

    private void autocenterPoint(ArrayList<Marker> ms){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Marker m : ms){
            builder.include(m.getPosition());
        }
        LatLngBounds bounds = builder.build();

        int padding = 50; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(cu);

    }

    private void autocenterPolygon(ArrayList<Polyline> ms){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Polyline m : ms){
            List<LatLng> temp = m.getPoints();
            builder.include(temp.get(0));
        }
        LatLngBounds bounds = builder.build();

        int padding = 50; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(cu);

    }

    public void loadAllMarkers(ArrayList<Coordinate> list){
        if(list != null){
            for(Coordinate c : list){
                Marker m = null;
                MarkerOptions mo = new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(c.Latitude), Double.parseDouble(c.Longitude)))
                        .title("Risk: " + c.Risk);
                if(c.Risk.toLowerCase().contains("high")) {
                    m = map.addMarker(mo);

                }else if(c.Risk.toLowerCase().contains("mod")){
                    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_orange));
                    m = map.addMarker(mo);
                }else if(c.Risk.toLowerCase().contains("low")){
                    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_green));
                    m = map.addMarker(mo);
                }else if(c.Risk.toLowerCase().contains("no")){
                    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_grey));
                    m = map.addMarker(mo);
                }


                if(m != null) {
                    m.setVisible(false);
                    markers.add(m);
                }
            }
            if(markers.size()>0) {
                autocenterPoint(markers);
            }
        }

    }

    public void toggleMarkers(boolean b, String title){
        if(markers != null && markers.size()>0){
            for(Marker m : markers){
                if (m.getTitle().toLowerCase().contains(title)) {
                    m.setVisible(b);
                }
            }
            autocenterPoint(markers);
        }
    }


}