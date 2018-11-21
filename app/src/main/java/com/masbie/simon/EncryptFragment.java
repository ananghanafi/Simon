package com.masbie.simon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import static com.google.zxing.BarcodeFormat.CODE_128;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EncryptFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EncryptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EncryptFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText keyEnc, plainEnc, chipEnc;
    Button btEnc, btEncQr, btEncbar;
    ImageView qrCode, barcode;
    TextView textView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EncryptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EncryptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EncryptFragment newInstance(String param1, String param2) {
        EncryptFragment fragment = new EncryptFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        keyEnc = (EditText) view.findViewById(R.id.keyEnc);
        plainEnc = (EditText) view.findViewById(R.id.plainEnc);
        chipEnc = (EditText) view.findViewById(R.id.chipEnc);
        btEnc = (Button) view.findViewById(R.id.btEnc);
        btEncQr = (Button) view.findViewById(R.id.btGenqr);
        btEncbar = (Button) view.findViewById(R.id.btGenbar);
        qrCode = (ImageView) view.findViewById(R.id.qrGambar);
        barcode = (ImageView) view.findViewById(R.id.barcodeGambar);


        btEncQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String hasil =  keyEnc.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(hasil, BarcodeFormat.QR_CODE, 800, 800);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    qrCode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });
        btEncbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hasil = keyEnc.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix1 = multiFormatWriter.encode(hasil, BarcodeFormat.CODE_128, 800,400);
                    BarcodeEncoder barcodeEncoder1 = new BarcodeEncoder();
                    Bitmap bitmap1 = barcodeEncoder1.createBitmap(bitMatrix1);
                    barcode.setImageBitmap(bitmap1);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });


    }

//    private void genQr() {
//        System.out.println("Coba Lagi");
//        BarcodeDetector detector =
//                new BarcodeDetector.Builder(getActivity().getApplicationContext())
//                        .setBarcodeFormats(Barcode.QR_CODE)
//                        .build();
//        if (!detector.isOperational()) {
//            textView.setText("Could not set up the detector!");
//            return;
//        }
//        Bitmap myBitmap = BitmapFactory.decodeResource(
//                getActivity().getApplicationContext().getResources(),
//                Barcode.QR_CODE);
//        qrCode.setImageBitmap(myBitmap);
//    }

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
        return inflater.inflate(R.layout.fragment_encrypt, container, false);
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
}
