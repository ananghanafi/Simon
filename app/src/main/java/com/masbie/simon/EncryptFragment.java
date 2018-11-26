package com.masbie.simon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView textView, chipEncT;
    LinearLayout linQR, liBar, linWaktu;
    long startTime, endTime, elapTime;
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
        // chipEnc = (EditText) view.findViewById(R.id.chipEnc);
        chipEncT = (TextView) view.findViewById(R.id.tChip);
        btEnc = (Button) view.findViewById(R.id.btEnc);
        btEncQr = (Button) view.findViewById(R.id.btGenqr);
        btEncbar = (Button) view.findViewById(R.id.btGenbar);
        qrCode = (ImageView) view.findViewById(R.id.qrGambar);
        barcode = (ImageView) view.findViewById(R.id.barcodeGambar);
        linQR = (LinearLayout) view.findViewById(R.id.linQR);
        liBar = (LinearLayout) view.findViewById(R.id.linBar);
        linWaktu = (LinearLayout) view.findViewById(R.id.linEncWaktu);
        textView = (TextView) view.findViewById(R.id.encWaktu);
//        linQR.setVisibility(View.INVISIBLE);
//        liBar.setVisibility(view.INVISIBLE);
        chipEncT.setText("Coba");

        Word_Size = 24;
        Keywords = 3;
        Const_Seq = 0;
        Rounds = 36;
        tempCost = "Z0";
        c = 0xfffc;
        f = 0xffff;
        indexAwal = 12;
        HexWordSize = 6;
        bit = 24;
        btEnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ambilKey = keyEnc.getText().toString();
                String ambilPlaint = plainEnc.getText().toString();
                Toast.makeText(getActivity(), "Tes ", Toast.LENGTH_SHORT).show();
                if (ambilKey == null || ambilPlaint == null || ambilKey.equals("") ||
                        ambilPlaint.equals("") || ambilKey.equals(" ") || ambilPlaint.equals(" ")) {

                    Toast.makeText(getActivity(), "Setealah Tes ", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Warning..!!!");
                    builder.setMessage("Isi key dan plaint text dengan benar").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert1 = builder.create();
                    alert1.show();

                } else {
                    //  startTime = System.nanoTime();
                    startTime = System.currentTimeMillis();
//                    keyExpansion(ambilKey);
//                    encrypt();
                    for (int j = 0; j < 1000; j++) {
                        Toast.makeText(getActivity(), "Coba Count " + j, Toast.LENGTH_SHORT).show();
                    }
                    //  endTime = System.nanoTime();
                    endTime = System.currentTimeMillis();
                    elapTime = endTime - startTime;
                    textView.setText("Waktu untuk encrypt = " + elapTime + " ms");
                    linWaktu.setVisibility(View.VISIBLE);
                }
            }
        });
        btEncQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hasil = chipEncT.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(hasil, BarcodeFormat.QR_CODE, 800, 800);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    linQR.setVisibility(View.VISIBLE);
                    qrCode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });
        btEncbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hasil = chipEncT.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix1 = multiFormatWriter.encode(hasil, BarcodeFormat.CODE_128, 800, 400);
                    BarcodeEncoder barcodeEncoder1 = new BarcodeEncoder();
                    Bitmap bitmap1 = barcodeEncoder1.createBitmap(bitMatrix1);
                    liBar.setVisibility(View.VISIBLE);
                    barcode.setImageBitmap(bitmap1);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });


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
