package com.masbie.simon;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SimonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SimonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimonFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MaterialSpinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7;
    String strSpinner1[], strSpinner2[], strSpinner3[], strSpinner4[], strSpinner5[], strSpinner6[];
    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10,
            editText11, editText12, editText13, editText14, editText15, editText16;
    Button encryptBtn, decryptBtn;
    TextView textView1, textView2, textView3;
    int posisi1 = 0, posisi2 = 0;

    // Inisialisai Variabel Global
    int blockSize = 32;
    int keySize = 64;
    int wordSize = 16;
    int keyWords = 4;
    int zSeq = 0;
    int rounds = 32;

    String tempConst = "";
    int cInt, fInt;
    long cLong, fLong;
//    int bit;

    int[][] z = {
            {1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0},
            {1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0},
            {1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1},
            {1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1},
            {1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}};

    int[] key1;
    long[] key2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SimonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SimonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimonFragment newInstance(String param1, String param2) {
        SimonFragment fragment = new SimonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner1 = (MaterialSpinner) view.findViewById(R.id.spinner);
        spinner2 = (MaterialSpinner) view.findViewById(R.id.spinner2);
        editText1 = (EditText) view.findViewById(R.id.word);
        editText2 = (EditText) view.findViewById(R.id.consts);
        editText3 = (EditText) view.findViewById(R.id.keyword);
        editText14 = (EditText) view.findViewById(R.id.round);
        editText4 = (EditText) view.findViewById(R.id.key);
        editText5 = (EditText) view.findViewById(R.id.Key0);
        editText6 = (EditText) view.findViewById(R.id.Key1);
        editText7 = (EditText) view.findViewById(R.id.Key2);
        editText8 = (EditText) view.findViewById(R.id.Key3);
        editText9 = (EditText) view.findViewById(R.id.plaint);
        editText10 = (EditText) view.findViewById(R.id.up);
        editText11 = (EditText) view.findViewById(R.id.down);
        editText12 = (EditText) view.findViewById(R.id.up1);
        editText13 = (EditText) view.findViewById(R.id.down1);
        textView1 = (TextView) view.findViewById(R.id.cekTulis);
        encryptBtn = (Button) view.findViewById(R.id.btEncrypt);



        strSpinner1 = new String[]{
                "0", "32", "48", "64", "96", "128"
        };
        spinner1.setItems(strSpinner1);
        spinner1.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // posisi = position;
                posisi1 = position;
                Snackbar.make(view, "Nilai " + strSpinner1[position], Snackbar.LENGTH_LONG).show();
                // String setHarga = harga.setText();
                //     Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        spinner1.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();
            }
        });
        strSpinner2 = new String[]{
                "0", "32", "48", "64", "96", "128", "144", "192", "256"
        };
        spinner2.setItems(strSpinner2);
        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // posisi = position;
                posisi2 = position;
                Snackbar.make(view, "Nilai " + strSpinner2[position], Snackbar.LENGTH_LONG).show();
                // String setHarga = harga.setText();
                //     Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                pisah();

            }
        });
        spinner2.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();

            }
        });

    }

    public void pisah() {
        
        blockSize = Integer.parseInt(strSpinner1[posisi1]);
        keySize = Integer.parseInt(strSpinner2[posisi2]);
        System.out.println("keySize apa");
        System.out.println("keySize " + strSpinner1[posisi1]);
//        if (strSpinner1[posisi1].equals("32")) {
//            System.out.println("keySize kk");
//        }
        if (strSpinner1[posisi1].equals("32") || strSpinner1[posisi1].equals("48") || strSpinner1[posisi1].equals("64")) {
            if (strSpinner1[posisi1].equals("32") && strSpinner2[posisi2].equals("64")) {
                wordSize = 16;
                keyWords = 4;
                zSeq = 0;
                rounds = 32;
                tempConst = "Z0";
                cInt = 0xfffc;
                fInt = 0xffff;

//            editText1.setText(wordSize);
//            editText2.setText(zSeq);
//            editText3.setText(keyWords);
//            editText14.setText(rounds);
//            textView1.setText(wordSize);

                System.out.println("keySize == 32 && keyWords ==4 " + Integer.parseInt(strSpinner1[posisi1]) + " dan " + tempConst);
            } else if (strSpinner1[posisi1].equals("48") && strSpinner2[posisi2].equals("72")) {
                wordSize = 24;
                keyWords = 3;
                zSeq = 0;
                rounds = 36;
                tempConst = "Z0";
                cInt = 0xfffc;
                fInt = 0xffff;
//            editText1.setText(wordSize);
//            editText2.setText(zSeq);
//            editText3.setText(keyWords);
//            editText14.setText(rounds);
            } else if (strSpinner1[posisi1].equals("48") && strSpinner2[posisi2].equals("96")) {
                wordSize = 24;
                keyWords = 4;
                zSeq = 1;
                rounds = 36;
                tempConst = "Z1";
                cInt = 0xfffffc;
                fInt = 0xffffff;
//            editText1.setText(wordSize);
//            editText2.setText(zSeq);
//            editText3.setText(keyWords);
//            editText14.setText(rounds);
            } else if (strSpinner1[posisi1].equals("64") && strSpinner2[posisi2].equals("96")) {
                wordSize = 32;
                keyWords = 3;
                zSeq = 2;
                rounds = 42;
                tempConst = "Z2";
                cInt = 0xfffffffc;
                fInt = 0xffffffff;
//            editText1.setText(wordSize);
//            editText2.setText(zSeq);
//            editText3.setText(keyWords);
//            editText14.setText(rounds);
            } else if (strSpinner1[posisi1].equals("64") && strSpinner2[posisi2].equals("128")) {
                wordSize = 32;
                keyWords = 4;
                zSeq = 3;
                rounds = 44;
                tempConst = "Z3";
                cInt = 0xfffffffc;
                fInt = 0xffffffff;
//            editText1.setText(wordSize);
//            editText2.setText(zSeq);
//            editText3.setText(keyWords);
//            editText14.setText(rounds);
            }
//            key1 = keyExpansion1(String.valueOf(editText4));
//            encrypt();

        } else if (strSpinner1[posisi1].equals("96") || strSpinner2[posisi2].equals("128")) {

            if (strSpinner1[posisi1].equals("96") && strSpinner2[posisi2].equals("96")) {
                wordSize = 48;
                keyWords = 2;
                zSeq = 2;
                rounds = 52;
                tempConst = "Z2";
                cLong = 0xfffffffffffcl;
                fLong = 0xffffffffffffl;
//            editText1.setText(wordSize);
//            editText2.setText(zSeq);
//            editText3.setText(keyWords);
//            editText14.setText(rounds);
            } else if (strSpinner1[posisi1].equals("96") && strSpinner2[posisi2].equals("144")) {
                wordSize = 48;
                keyWords = 3;
                zSeq = 3;
                rounds = 54;
                tempConst = "Z3";
                cLong = 0xfffffffffffcl;
                fLong = 0xffffffffffffl;
//            editText1.setText(wordSize);
//            editText2.setText(zSeq);
//            editText3.setText(keyWords);
//            editText14.setText(rounds);
            } else if (strSpinner1[posisi1].equals("128") && strSpinner2[posisi2].equals("128")) {
                wordSize = 64;
                keyWords = 2;
                zSeq = 2;
                rounds = 68;
                tempConst = "Z2";
                cLong = 0xfffffffffffffffcl;
                fLong = 0xffffffffffffffffl;
//            editText1.setText(wordSize);
//            editText2.setText(zSeq);
//            editText3.setText(keyWords);
//            editText14.setText(rounds);
            } else if (strSpinner1[posisi1].equals("128") && strSpinner2[posisi2].equals("192")) {
                wordSize = 64;
                keyWords = 3;
                zSeq = 3;
                rounds = 69;
                tempConst = "Z3";
                cLong = 0xfffffffffffffffcl;
                fLong = 0xffffffffffffffffl;
//            editText1.setText(wordSize);
//            editText2.setText(zSeq);
//            editText3.setText(keyWords);
//            editText14.setText(rounds);
            } else if (strSpinner1[posisi1].equals("128") && strSpinner2[posisi2].equals("256")) {
                wordSize = 64;
                keyWords = 4;
                zSeq = 4;
                rounds = 72;
                tempConst = "Z4";
                cLong = 0xfffffffffffffffcl;
                fLong = 0xffffffffffffffffl;
//            editText1.setText(wordSize);
//            editText2.setText(zSeq);
//            editText3.setText(keyWords);
//            editText14.setText(rounds);
            }
//            key2 = keyExpansion2(String.valueOf(editText4));
//            encrypt();

        } else {
            wordSize = 0;
            keyWords = 0;
            zSeq = 0;
            rounds = 0;
            tempConst = "none";
            cInt = 0xfffc;
            fInt = 0xffff;
//            editText1.setText(wordSize);
//            editText2.setText(zSeq);
//            editText3.setText(keyWords);
//            editText14.setText(rounds);
        }

    }


    public void pisah2() {
        if (strSpinner1[posisi1].equals("64") && keyWords == 4) {
            System.out.println("keySize == 64 && keyWords ==4 " + keySize + " dan " + keyWords + " ccc " + rounds);
        } else if (strSpinner1[posisi1].equals("") && keyWords == 4) {

        } else if (strSpinner1[posisi1].equals("") && keyWords == 4) {

        } else if (strSpinner1[posisi1].equals("") && keyWords == 4) {

        } else if (strSpinner1[posisi1].equals("") && keyWords == 4) {

        } else if (strSpinner1[posisi1].equals("") && keyWords == 4) {

        } else if (strSpinner1[posisi1].equals("") && keyWords == 4) {

        } else if (strSpinner1[posisi1].equals("") && keyWords == 4) {

        } else if (strSpinner1[posisi1].equals("") && keyWords == 4) {

        } else if (strSpinner1[posisi1].equals("") && keyWords == 4) {

        } else if (strSpinner1[posisi1].equals("") && keyWords == 4) {

        } else if (strSpinner1[posisi1].equals("") && keyWords == 4) {

        }
    }

    private void encrypt() {
        int hexBlockSize = blockSize / 4;
        if (blockSize == 32 || blockSize == 48 || blockSize == 64) {
            int x = 0, y = 0, tmp;
            x = Integer.parseInt(String.valueOf(editText9).substring(0, hexBlockSize / 2), 16);
            y = Integer.parseInt(String.valueOf(editText9).substring(hexBlockSize / 2, hexBlockSize), 16);
            for (int j = 0; j < rounds; j++) {
                tmp = x;
                x = (y ^ (rotateLeft(x, 1, wordSize) & rotateLeft(x, 8, wordSize)) ^ rotateLeft(x, 2, wordSize) ^ key1[j]) & fInt;//x = y XOR ((S^1)x AND (S^8)x) XOR (S^2)x XOR k[i]
                y = tmp;//y = x
            }
            textView1.setText(String.format("%0"+(hexBlockSize / 2)+"x", x)+String.format("%0"+(hexBlockSize / 2)+"x", y));
        } else if (blockSize == 96 || blockSize == 128) {
            long x = 0, y = 0, tmp;
            x = Long.parseLong(String.valueOf(editText9).substring(0, hexBlockSize / 2), 16);
            y = Long.parseLong(String.valueOf(editText9).substring(hexBlockSize / 2, hexBlockSize), 16);
            for (int j = 0; j < rounds; j++) {
                tmp = x;
                x = (y ^ (rotateLeft(x, 1, wordSize) & rotateLeft(x, 8, wordSize)) ^ rotateLeft(x, 2, wordSize) ^ key1[j]) & fLong;//x = y XOR ((S^1)x AND (S^8)x) XOR (S^2)x XOR k[i]
                y = tmp;//y = x
            }
            textView1.setText(String.format("%0"+(hexBlockSize / 2)+"x", x)+String.format("%0"+(hexBlockSize / 2)+"x", y));
        }
    }

    private void decrypt() {
        int x = 0, y = 0, tmp;
        for (int j = rounds - 1; j >= 0; j--) {
            tmp = y;
            y = (x ^ (rotateLeft(y, 1, wordSize) & rotateLeft(y, 8, wordSize)) ^ rotateLeft(y, 2, wordSize) ^ key1[j]) & 0xFFFFFF;//y = x XOR ((S^1)y AND (S^8)y) XOR (S^2)y XOR k[i]
            x = tmp;//x = y
        }

    }

    public int[] keyExpansion1(String key) {
        int hexWordSize = wordSize / 4;
        int hexKeySize = keySize / 4;
        int firstIndex = hexKeySize - hexWordSize;
        int[] k = new int[rounds];
        key = String.format("%1$" + hexKeySize + "s", key).replace(' ', '0');
        /*Inisialisasi k[keyWords-1]..k[0]*/
        for (int i = 0; i < keyWords; i++) {
            int index = firstIndex - (i * hexKeySize);
            k[i] = Integer.parseInt(key.substring(index, index + 6), 16) & fInt;
//            System.out.println(key.substring(index, index + 6) + "|" + Integer.toBinaryString(k[i]));
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
        }
        return k;
    }

    public long[] keyExpansion2(String key) {
        int hexWordSize = wordSize / 4;
        int hexBlockSize = blockSize / 4;
        int hexKeySize = keySize / 4;
        int firstIndex = hexKeySize - hexWordSize;
        long[] k = new long[rounds];
        key = String.format("%1$" + hexKeySize + "s", key).replace(' ', '0');
        /*Inisialisasi k[keyWords-1]..k[0]*/
        for (int i = 0; i < keyWords; i++) {
            int index = firstIndex - (i * hexKeySize);
            k[i] = Long.parseLong(key.substring(index, index + 6), 16) & fLong;
//            System.out.println(key.substring(index, index + 6) + "|" + Integer.toBinaryString(k[i]));
        }
        /*Ekspansi Kunci*/
        for (int i = keyWords; i < rounds; i++) {
            long tmp = rotateRight(k[i - 1], 3, wordSize);
//            System.out.println(Integer.toBinaryString(k[i - 1]) + "|" + Integer.toBinaryString(tmp));
            if (keyWords == 4) {
                tmp ^= k[i - 3];
            }
//            System.out.println(Integer.toBinaryString(tmp) + "|" + Integer.toBinaryString(rotateRight(tmp, 1, 24)));
            tmp = tmp ^ rotateRight(tmp, 1, wordSize);
//            System.out.println(Integer.toBinaryString(tmp));
//            System.out.println("");
            k[i] = (tmp ^ k[i - keyWords] ^ z[zSeq][(i - keyWords) % 62] ^ cLong) & fLong;
//            System.out.println("tmp = " + Integer.toBinaryString(tmp));
//            System.out.println("k[" + (i - keyWords) + "] = " + Integer.toBinaryString(k[i - keyWords]));
//            System.out.println("z0[" + ((i - keyWords) % 62) + "] = " + Integer.toBinaryString(z0[(i - keyWords) % 62]));
//            System.out.println("cInt = " + Integer.toBinaryString(cInt));
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

    public long rotateRight(long n, int s, int bits) {
        return ((n >>> s) | (n << (bits - s)));//Rotasi nilai bit RGB per pixel ke kanan
    }

    public long rotateLeft(long n, int s, int bits) {
        return ((n << s) | (n >>> (bits - s)));//Rotasi nilai bit RGB per pixel ke kiri
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View coba = inflater.inflate(R.layout.fragment_simon, container, false);

//        textView1 = (TextView) coba.findViewById(R.id.cekTulis);
//        textView1.setText("ganti ini");
        return coba;
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
