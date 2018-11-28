package com.masbie.simon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.integration.android.*;
import com.masbie.simon.ui.camera.CameraSource;
import com.masbie.simon.ui.camera.CameraSourcePreview;

import com.masbie.simon.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DecryptFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DecryptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//public class DecryptFragment extends Fragment implements View.OnTouchListener, BarcodeGraphicTracker.BarcodeUpdateListener {
public class DecryptFragment extends Fragment implements ZXingScannerView.ResultHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText keyDec, hasilScanDec, hasilDec;
    Button btDec, cobaBar;
    CardView btCard;
    private ZXingScannerView mScannerView;
    Intent tampil;
    Bundle extras;
    TextView textView, hasilDecT;
    LinearLayout linDecWakt;
    private long startTime, endTime, elapTime;
    String gg;
    int Block_Size = 0;
    int Key_Size = 64;
    int Word_Size = 0;
    int Keywords = 4;
    int Const_Seq = 0;
    int Rounds = 0;
    String tempCost = "";
    int c, f;
    int indexAwal, HexWordSize;
    int bit;
    String ambilKey, hasilScanS;

    private OnFragmentInteractionListener mListener;
//    private static final String TAG = "Barcode-reader";
//
//    // intent request code to handle updating play services if needed.
//    private static final int RC_HANDLE_GMS = 9001;
//
//    // permission request codes need to be < 256
//    private static final int RC_HANDLE_CAMERA_PERM = 2;
//
//    // constants used to pass extra data in the intent
//    public static final String AutoFocus = "AutoFocus";
//    public static final String UseFlash = "UseFlash";
//    public static final String BarcodeObject = "Barcode";
//
//    private CameraSource mCameraSource;
//    private CameraSourcePreview mPreview;
//    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;
//
//    // helper objects for detecting taps and pinches.
//    private ScaleGestureDetector scaleGestureDetector;
//    private GestureDetector gestureDetector;

    public DecryptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DecryptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DecryptFragment newInstance(String param1, String param2) {
        DecryptFragment fragment = new DecryptFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Decrypt");
        mScannerView = new ZXingScannerView(getActivity());   // Programmatically initialize the scanner view

        keyDec = (EditText) view.findViewById(R.id.keyDec);
        //  hasilScanDec = (EditText) view.findViewById(R.id.hasilScan);
        //hasilDec = (EditText) view.findViewById(R.id.hasilDec);
        btDec = (Button) view.findViewById(R.id.btDecrypt);
        btCard = (CardView) view.findViewById(R.id.cobBarcode);
        //  cobaBar = (Button) view.findViewById(R.id.cobBarcode);
        textView = (TextView) view.findViewById(R.id.hasilScanT);
        hasilDecT = (TextView) view.findViewById(R.id.hasilDecT);
        linDecWakt = (LinearLayout) view.findViewById(R.id.lindecWaktu);
        gg = getActivity().getIntent().getStringExtra("ambilNilai");
        //    gg = getArguments().getString("my_key");

        textView.setText(String.valueOf(gg));
        btDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ambilKey == null || gg == null || ambilKey.equals("") ||
                        gg.equals("") || ambilKey.equals(" ") || gg.equals(" ")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Warning..!!!");
                    builder.setMessage("Hasil scan dan key nya harus terisi").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert1 = builder.create();
                    alert1.show();

                } else {
                    //startTime = System.nanoTime();
                    startTime = System.currentTimeMillis();
//                    keyExpansion(ambilKey);
//                    encrypt();
                    for (int j = 0; j < 1070; j++) {
                        Toast.makeText(getActivity(), "Coba COunt " + j, Toast.LENGTH_SHORT).show();
                    }
                    // endTime = System.nanoTime();
                    endTime = System.currentTimeMillis();
                    elapTime = endTime - startTime;
                    textView.setText("Waktu untuk encrypt = " + elapTime + " ms");
                    linDecWakt.setVisibility(View.VISIBLE);
                }
            }
        });
        btCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hh = new Intent(getActivity(), ZxingBarcode.class);
                startActivity(hh);
//                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
//                intentIntegrator.setCameraId(0);
//                intentIntegrator.setPrompt("Scan");
//                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.CODE_128, IntentIntegrator.QR_CODE);
//                intentIntegrator.setBeepEnabled(true);
//                intentIntegrator.setBarcodeImageEnabled(false);
//                intentIntegrator.initiateScan();
            }
        });
//        mPreview = (CameraSourcePreview) view.findViewById(R.id.preview);
//        mGraphicOverlay = (GraphicOverlay<BarcodeGraphic>) view.findViewById(R.id.graphicOverlay);
//        // read parameters from the intent used to launch the activity.
//        boolean autoFocus = getActivity().getIntent().getBooleanExtra(AutoFocus, true);
//        boolean useFlash = getActivity().getIntent().getBooleanExtra(UseFlash, false);
//
//        // Check for the camera permission before accessing the camera.  If the
//        // permission is not granted yet, request permission.
//        int rc = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
//        if (rc == PackageManager.PERMISSION_GRANTED) {
//            createCameraSource(autoFocus, useFlash);
//        } else {
//            requestCameraPermission();
//        }
//
//        gestureDetector = new GestureDetector(getActivity(), new DecryptFragment.CaptureGestureListener());
//        scaleGestureDetector = new ScaleGestureDetector(getActivity(), new DecryptFragment.ScaleListener());
//        view.setOnTouchListener(this);
//
//        Snackbar.make(mGraphicOverlay, "Tap to capture. Pinch/Stretch to zoom",
//                Snackbar.LENGTH_LONG)
//                .show();
////        Barcode thisCode = barcodes.valueAt(0);
////        TextView txtView = (TextView) findViewById(R.id.txtContent);
////        txtView.setText(thisCode.rawValue);
    }

    private void encrypt() {
        int x = 0, y = 0, tmp;
        for (int j = Rounds - 1; j >= 0; j--) {
            tmp = y;
            //  y = (x ^ (rotateLeft(y, 1, 24) & rotateLeft(y, 8, 24)) ^ rotateLeft(y, 2, 24) ^ key[j]) & f;//y = x XOR ((S^1)y AND (S^8)y) XOR (S^2)y XOR k[i]
            x = tmp;//x = y
        }

    }

    private void decrypt() {
        int x = 0, y = 0, tmp;
        for (int j = Rounds - 1; j >= 0; j--) {
            tmp = y;
            //       y = (x ^ (rotateLeft(y, 1, 24) & rotateLeft(y, 8, 24)) ^ rotateLeft(y, 2, 24) ^ key[j]) & 0xFFFFFF;//y = x XOR ((S^1)y AND (S^8)y) XOR (S^2)y XOR k[i]
            x = tmp;//x = y
        }

    }

    public int[] keyExpansion(String key) {
        int[] k = new int[Rounds];
        /*Inisialisasi k[keyWords-1]..k[0]*/
        for (int i = 0; i < Keywords; i++) {
            int index = indexAwal - (i * HexWordSize);
            k[i] = Integer.parseInt(key.substring(index, index + 6), 16) & f;
//            System.out.println(key.substring(index, index + 6) + "|" + Integer.toBinaryString(k[i]));
        }
        /*Ekspansi Kunci*/
        for (int i = Keywords; i < Rounds; i++) {
            int tmp = rotateRight(k[i - 1], 3, bit);
//            System.out.println(Integer.toBinaryString(k[i - 1]) + "|" + Integer.toBinaryString(tmp));
            if (Keywords == 4) {
                tmp ^= k[i - 3];
            }
//            System.out.println(Integer.toBinaryString(tmp) + "|" + Integer.toBinaryString(rotateRight(tmp, 1, 24)));
            tmp = tmp ^ rotateRight(tmp, 1, bit);
//            System.out.println(Integer.toBinaryString(tmp));
//            System.out.println("");
            //k[i] = (tmp ^ k[i - Keywords] ^ z0[(i - Keywords) % 62] ^ c) & 0xFFFFFF;
//            System.out.println("tmp = " + Integer.toBinaryString(tmp));
//            System.out.println("k[" + (i - keyWords) + "] = " + Integer.toBinaryString(k[i - keyWords]));
//            System.out.println("z0[" + ((i - keyWords) % 62) + "] = " + Integer.toBinaryString(z0[(i - keyWords) % 62]));
//            System.out.println("c = " + Integer.toBinaryString(c));
//            System.out.println("k[" + i + "] = " + Integer.toBinaryString(k[i]));
//            System.out.println("");
        }
        return k;
    }

    public int rotateRight(int n, int s, int bits) {
        return ((n >>> s) | (n << (bits - s)));//Rotasi nilai bit RGB per pixel ke kanan
    }

    public int rotateLeft(int n, int s, int bits) {
        return ((n << s) | (n >>> (bits - s)));//Rotasi nilai bit RGB per pixel ke kiri
    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_decrypt, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        android.support.v7.app.AlertDialog alert1 = builder.create();
        alert1.show();
    }

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        boolean b = scaleGestureDetector.onTouchEvent(motionEvent);
//
//        boolean c = gestureDetector.onTouchEvent(motionEvent);
//
//        return b || c || view.onTouchEvent(motionEvent);
//    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
//    private void requestCameraPermission() {
//        Log.w(TAG, "Camera permission is not granted. Requesting permission");
//
//        final String[] permissions = new String[]{Manifest.permission.CAMERA};
//
//        if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                Manifest.permission.CAMERA)) {
//            ActivityCompat.requestPermissions(getActivity(), permissions, RC_HANDLE_CAMERA_PERM);
//            return;
//        }
//
//        final Activity thisActivity = getActivity();
//
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivityCompat.requestPermissions(thisActivity, permissions,
//                        RC_HANDLE_CAMERA_PERM);
//            }
//        };
//
//        getActivity().findViewById(R.id.topLayout1).setOnClickListener(listener);
//        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
//                Snackbar.LENGTH_INDEFINITE)
//                .setAction(R.string.ok, listener)
//                .show();
//    }


    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     * <p>
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     //     */
//    @SuppressLint("InlinedApi")
//    private void createCameraSource(boolean autoFocus, boolean useFlash) {
//        Context context = getActivity().getApplicationContext();
//
//        // A barcode detector is created to track barcodes.  An associated multi-processor instance
//        // is set to receive the barcode detection results, track the barcodes, and maintain
//        // graphics for each barcode on screen.  The factory is used by the multi-processor to
//        // create a separate tracker instance for each barcode.
//        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
//        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay, getActivity());
//        barcodeDetector.setProcessor(
//                new MultiProcessor.Builder<>(barcodeFactory).build());
//
//        if (!barcodeDetector.isOperational()) {
//            // Note: The first time that an app using the barcode or face API is installed on a
//            // device, GMS will download a native libraries to the device in order to do detection.
//            // Usually this completes before the app is run for the first time.  But if that
//            // download has not yet completed, then the above call will not detect any barcodes
//            // and/or faces.
//            //
//            // isOperational() can be used to check if the required native libraries are currently
//            // available.  The detectors will automatically become operational once the library
//            // downloads complete on device.
//            Log.w(TAG, "Detector dependencies are not yet available.");
//            System.out.println("Detector dependencies are not yet available.");
//
//            // Check for low storage.  If there is low storage, the native library will not be
//            // downloaded, so detection will not become operational.
//            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
//            boolean hasLowStorage = getActivity().registerReceiver(null, lowstorageFilter) != null;
//
//            if (hasLowStorage) {
//                Toast.makeText(getActivity(), R.string.low_storage_error, Toast.LENGTH_LONG).show();
//                Log.w(TAG, getString(R.string.low_storage_error));
//            }
//        }
//
//        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
//        // to other detection examples to enable the barcode detector to detect small barcodes
//        // at long distances.
//        CameraSource.Builder builder = new CameraSource.Builder(getActivity().getApplicationContext(), barcodeDetector)
//                .setFacing(CameraSource.CAMERA_FACING_BACK)
//                .setRequestedPreviewSize(1600, 1024)
//                .setRequestedFps(15.0f);
//
//        // make sure that auto focus is an available option
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//            builder = builder.setFocusMode(
//                    autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);
//        }
//
//        mCameraSource = builder
//                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
//                .build();
//    }

    /**
     * Restarts the camera.
     */
//    @Override
//    public void onResume() {
//        super.onResume();
//        startCameraSource();
//    }

    /**
     * Stops the camera.
     */
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (mPreview != null) {
//            mPreview.stop();
//        }
//    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    //@Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mPreview != null) {
//            mPreview.release();
//        }
//    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode != RC_HANDLE_CAMERA_PERM) {
//            Log.d(TAG, "Got unexpected permission result: " + requestCode);
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//            return;
//        }
//
//        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "Camera permission granted - initialize the camera source");
//            // we have permission, so create the camerasource
//            boolean autoFocus = getActivity().getIntent().getBooleanExtra(AutoFocus, true);
//            boolean useFlash = getActivity().getIntent().getBooleanExtra(UseFlash, false);
//            createCameraSource(autoFocus, useFlash);
//            return;
//        }
//
//        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
//                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));
//
//        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                getActivity().finish();
//            }
//        };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Multitracker sample")
//                .setMessage(R.string.no_camera_permission)
//                .setPositiveButton(R.string.ok, listener)
//                .show();
//    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
//    private void startCameraSource() throws SecurityException {
//        // check that the device has play services available.
//        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
//                getActivity().getApplicationContext());
//        if (code != ConnectionResult.SUCCESS) {
//            Dialog dlg =
//                    GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), code, RC_HANDLE_GMS);
//            dlg.show();
//        }
//
//        if (mCameraSource != null) {
//            try {
//                mPreview.start(mCameraSource, mGraphicOverlay);
//            } catch (IOException e) {
//                Log.e(TAG, "Unable to start camera source.", e);
//                mCameraSource.release();
//                mCameraSource = null;
//            }
//        }
//    }

    /**
     * onTap returns the tapped barcode result to the calling Activity.
     *
     * @param rawX - the raw position of the tap
     * @param rawY - the raw position of the tap.
     * @return true if the activity is ending.
     */
//    private boolean onTap(float rawX, float rawY) {
//        // Find tap point in preview frame coordinates.
//        int[] location = new int[2];
//        mGraphicOverlay.getLocationOnScreen(location);
//        float x = (rawX - location[0]) / mGraphicOverlay.getWidthScaleFactor();
//        float y = (rawY - location[1]) / mGraphicOverlay.getHeightScaleFactor();
//
//        // Find the barcode whose center is closest to the tapped point.
//        Barcode best = null;
//        float bestDistance = Float.MAX_VALUE;
//        for (BarcodeGraphic graphic : mGraphicOverlay.getGraphics()) {
//            Barcode barcode = graphic.getBarcode();
//            if (barcode.getBoundingBox().contains((int) x, (int) y)) {
//                // Exact hit, no need to keep looking.
//                best = barcode;
//                break;
//            }
//            float dx = x - barcode.getBoundingBox().centerX();
//            float dy = y - barcode.getBoundingBox().centerY();
//            float distance = (dx * dx) + (dy * dy);  // actually squared distance
//            if (distance < bestDistance) {
//                best = barcode;
//                bestDistance = distance;
//            }
//        }
//
//        if (best != null) {
//            Intent data = new Intent();
//            data.putExtra(BarcodeObject, best);
//            getActivity().setResult(CommonStatusCodes.SUCCESS, data);
//            getActivity().finish();
//            return true;
//        }
//        return false;
//    }
//
//    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onSingleTapConfirmed(MotionEvent e) {
//            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
//        }
//    }
//
//    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
//
//        /**
//         * Responds to scaling events for a gesture in progress.
//         * Reported by pointer motion.
//         *
//         * @param detector The detector reporting the event - use this to
//         *                 retrieve extended info about event state.
//         * @return Whether or not the detector should consider this event
//         * as handled. If an event was not handled, the detector
//         * will continue to accumulate movement until an event is
//         * handled. This can be useful if an application, for example,
//         * only wants to update scaling factors if the change is
//         * greater than 0.01.
//         */
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//            return false;
//        }
//
//        /**
//         * Responds to the beginning of a scaling gesture. Reported by
//         * new pointers going down.
//         *
//         * @param detector The detector reporting the event - use this to
//         *                 retrieve extended info about event state.
//         * @return Whether or not the detector should continue recognizing
//         * this gesture. For example, if a gesture is beginning
//         * with a focal point outside of a region where it makes
//         * sense, onScaleBegin() may return false to ignore the
//         * rest of the gesture.
//         */
//        @Override
//        public boolean onScaleBegin(ScaleGestureDetector detector) {
//            return true;
//        }
//
//        /**
//         * Responds to the end of a scale gesture. Reported by existing
//         * pointers going up.
//         * <p/>
//         * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
//         * and {@link ScaleGestureDetector#getFocusY()} will return focal point
//         * of the pointers remaining on the screen.
//         *
//         * @param detector The detector reporting the event - use this to
//         *                 retrieve extended info about event state.
//         */
//        @Override
//        public void onScaleEnd(ScaleGestureDetector detector) {
//            mCameraSource.doZoom(detector.getScaleFactor());
//        }
//    }
//
//    @Override
//    public void onBarcodeDetected(Barcode barcode) {
//        //do something with barcode data returned
//    }
//      @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult intentResult = new IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
//        if (intentResult != null) {
//            if (intentResult.getContents() == null) {
//                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getActivity(), intentResult.getContents(), Toast.LENGTH_SHORT).show();
//            }
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }
}
