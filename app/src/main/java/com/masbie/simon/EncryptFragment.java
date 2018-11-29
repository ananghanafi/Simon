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
    int[] key1;
    int blockSize, keySize, wordSize, keyWords, zSeq, rounds, cInt, fInt;
    String tempConst = "Z0";
    int[][] z = {
            {1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0},
            {1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0},
            {1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1},
            {1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1},
            {1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}};

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
        getActivity().setTitle("Encrypt");
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
        blockSize = 32;
        keySize = 64;
        wordSize = 16;
        keyWords = 4;
        zSeq = 0;
        rounds = 32;
        tempConst = "Z0";
        cInt = 0xfffc;
        fInt = 0xffff;

//        linQR.setVisibility(View.INVISIBLE);
//        liBar.setVisibility(view.INVISIBLE);
        //  chipEncT.setText("Coba");
        keyEnc.setText("1918111009080100");
        plainEnc.setText("65656877");

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
                    startTime = System.nanoTime();
                    //startTime = System.currentTimeMillis();
                    key1 = keyExpansion(ambilKey);
                    encrypt();

                    endTime = System.nanoTime();
                    // endTime = System.currentTimeMillis();
                    elapTime = endTime - startTime;
                    textView.setText("Waktu untuk encrypt = " + elapTime + " nano second");
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
        int hexBlockSize = blockSize / 4;
        String cipherText = null;
        int x = 0, y = 0, tmp;
        x = Integer.parseInt(plainEnc.getText().toString().substring(0, hexBlockSize / 2), 16);
        y = Integer.parseInt(plainEnc.getText().toString().substring(hexBlockSize / 2, hexBlockSize), 16);
        for (int j = 0; j < rounds; j++) {
            tmp = x;
            x = (y ^ (rotateLeft(x, 1, wordSize) & rotateLeft(x, 8, wordSize)) ^ rotateLeft(x, 2, wordSize) ^ key1[j]) & fInt;//x = y XOR ((S^1)x AND (S^8)x) XOR (S^2)x XOR k[i]
            y = tmp;//y = x
        }
        cipherText = String.format("%0" + (hexBlockSize / 2) + "x", x) + String.format("%0" + (hexBlockSize / 2) + "x", y);
        chipEncT.setText(cipherText);
//        editCipherT.setText(cipherText);
    }


    public int[] keyExpansion(String key) {
        int hexWordSize = wordSize / 4;
        int hexKeySize = keySize / 4;
        int firstIndex = hexKeySize - hexWordSize;
//        System.out.println("dordor: "+hexWordSize+" "+hexKeySize+" "+firstIndex);
        int[] k = new int[rounds];
//            key = String.format("%1$" + hexKeySize + "s", key).replace(' ', '0');
        String kk = "asd : " + key;
        System.out.println("kry: " + kk);
        /*Inisialisasi k[keyWords-1]..k[0]*/
        for (int i = 0; i < keyWords; i++) {
            int index = firstIndex - (i * hexWordSize);
//            System.out.print("haloo: "+index+" ");
//            System.out.println(index+hexWordSize);
            k[i] = Integer.parseInt(key.substring(index, index + hexWordSize), 16) & fInt;
            System.out.println("key round_" + i + ": " + String.format("%0" + hexWordSize + "x", k[i]));

        }
        /*Ekspansi Kunci*/
        for (int i = keyWords; i < rounds; i++) {
            int tmp = rotateRight(k[i - 1], 3, wordSize);
//            System.out.println(Integer.toBinaryString(k[i - 1]) + "|" + Integer.toBinaryString(tmp));
            if (keyWords == 4) {
                tmp ^= k[i - 3];
            }
//            System.out.println(Integer.toBinaryString(tmp) + "|" + Integer.toBinaryString(rotateRight(tmp, 1, 24)));
            tmp = tmp ^ rotateRight(tmp, 1, wordSize);
//            System.out.println(Integer.toBinaryString(tmp));
//            System.out.println("");
            k[i] = (tmp ^ k[i - keyWords] ^ z[zSeq][(i - keyWords) % 62] ^ cInt) & fInt;
//            System.out.println("tmp = " + Integer.toBinaryString(tmp));
//            System.out.println("k[" + (i - keyWords) + "] = " + Integer.toBinaryString(k[i - keyWords]));
//            System.out.println("z0[" + ((i - keyWords) % 62) + "] = " + Integer.toBinaryString(z0[(i - keyWords) % 62]));
//            System.out.println("cInt = " + Integer.toBinaryString(cInt));
//            System.out.println("k[" + i + "] = " + Integer.toBinaryString(k[i]));
//            System.out.println("");
            System.out.println("key round_" + i + ": " + String.format("%0" + hexWordSize + "x", k[i]));
        }
        return k;
    }

    public int rotateRight(int n, int s, int bits) {
        return ((n >>> s) | (n << (bits - s))) & fInt;//Rotasi nilai bit RGB per pixel ke kanan
    }

    public int rotateLeft(int n, int s, int bits) {
        return ((n << s) | (n >>> (bits - s))) & fInt;//Rotasi nilai bit RGB per pixel ke kiri
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
