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
    TextView textView1, textView2, textView3;
    int posisi1 = 0, posisi2 = 0;
    int key_expansion[];
    // Inisialisai Variabel Global
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

    int[] z0 = {1, 1, 1, 1, 1, 0, 1, 0, 0, 0,
            1, 0, 0, 1, 0, 1, 0, 1, 1, 0,
            0, 0, 0, 1, 1, 1, 0, 0, 1, 1,
            0, 1, 1, 1, 1, 1, 0, 1, 0, 0,
            0, 1, 0, 0, 1, 0, 1, 0, 1, 1,
            0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0};
    int[] z1 = {1, 0, 0, 0, 1, 1, 1, 0, 1, 1,
            1, 1, 1, 0, 0, 1, 0, 0, 1, 1,
            0, 0, 0, 0, 1, 0, 1, 1, 0, 1,
            0, 1, 0, 0, 0, 1, 1, 1, 0, 1,
            1, 1, 1, 1, 0, 0, 1, 0, 0, 1,
            1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0};
    int[] z2 = {1, 0, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1,
            0, 1, 0, 0, 1, 0, 0, 1, 1, 0,
            0, 0, 1, 0, 1, 0, 0, 0, 0, 1,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 0,
            0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1};
    int[] z3 = {1, 1, 0, 1, 1, 0, 1, 1, 1, 0,
            1, 0, 1, 1, 0, 0, 0, 1, 1, 0,
            0, 1, 0, 1, 1, 1, 1, 0, 0, 0,
            0, 0, 0, 1, 0, 0, 1, 0, 0, 0,
            1, 0, 1, 0, 0, 1, 1, 1, 0, 0,
            1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1};
    int[] z4 = {1, 1, 0, 1, 0, 0, 0, 1, 1, 1,
            1, 0, 0, 1, 1, 0, 1, 0, 1, 1,
            0, 1, 1, 0, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 1, 0, 1, 1, 1, 0, 0,
            0, 0, 1, 1, 0, 0, 1, 0, 1, 0,
            0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1};
    int[] key, key2;


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
        System.out.println("Key_Size apa");
        System.out.println("Key_Size " + strSpinner1[posisi1]);
        if (strSpinner1[posisi1].equals("32")) {
            System.out.println("Key_Size kk");
        }
        if (strSpinner1[posisi1].equals("32") && strSpinner2[posisi2].equals("64")) {
            Word_Size = 16;
            Keywords = 4;
            Const_Seq = 0;
            Rounds = 32;
            tempCost = "Z0";
            c = 0xfffc;
            f = 0xffff;

//            editText1.setText(Word_Size);
//            editText2.setText(Const_Seq);
//            editText3.setText(Keywords);
//            editText14.setText(Rounds);
//            textView1.setText(Word_Size);

            System.out.println("Key_Size == 32 && Keywords ==4 " + Integer.parseInt(strSpinner1[posisi1]) + " dan " + tempCost);
        } else if (strSpinner1[posisi1].equals("48") && strSpinner2[posisi2].equals("72")) {
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
//            editText1.setText(Word_Size);
//            editText2.setText(Const_Seq);
//            editText3.setText(Keywords);
//            editText14.setText(Rounds);
        } else if (strSpinner1[posisi1].equals("48") && strSpinner2[posisi2].equals("96")) {
            Word_Size = 24;
            Keywords = 4;
            Const_Seq = 1;
            Rounds = 36;
            tempCost = "Z1";
            c = 0xfffffc;
            f = 0xffffff;
//            editText1.setText(Word_Size);
//            editText2.setText(Const_Seq);
//            editText3.setText(Keywords);
//            editText14.setText(Rounds);
        } else if (strSpinner1[posisi1].equals("64") && strSpinner2[posisi2].equals("96")) {
            Word_Size = 32;
            Keywords = 3;
            Const_Seq = 2;
            Rounds = 42;
            tempCost = "Z2";
            c = 0xfffffffc;
            f = 0xffffffff;
//            editText1.setText(Word_Size);
//            editText2.setText(Const_Seq);
//            editText3.setText(Keywords);
//            editText14.setText(Rounds);
        } else if (strSpinner1[posisi1].equals("64") && strSpinner2[posisi2].equals("128")) {
            Word_Size = 32;
            Keywords = 4;
            Const_Seq = 3;
            Rounds = 44;
            tempCost = "Z3";
            c = 0xfffffffc;
            f = 0xffffffff;
//            editText1.setText(Word_Size);
//            editText2.setText(Const_Seq);
//            editText3.setText(Keywords);
//            editText14.setText(Rounds);
        } else if (strSpinner1[posisi1].equals("96") && strSpinner2[posisi2].equals("96")) {
            Word_Size = 48;
            Keywords = 2;
            Const_Seq = 2;
            Rounds = 52;
            tempCost = "Z2";
            //    c = 0xfffffffffffc;
            //    f = 0xffffffffffff;
//            editText1.setText(Word_Size);
//            editText2.setText(Const_Seq);
//            editText3.setText(Keywords);
//            editText14.setText(Rounds);
        } else if (strSpinner1[posisi1].equals("96") && strSpinner2[posisi2].equals("144")) {
            Word_Size = 48;
            Keywords = 3;
            Const_Seq = 3;
            Rounds = 54;
            tempCost = "Z3";
            //    c = 0xfffffffffffc;
            //    f = 0xffffffffffff;
//            editText1.setText(Word_Size);
//            editText2.setText(Const_Seq);
//            editText3.setText(Keywords);
//            editText14.setText(Rounds);
        } else if (strSpinner1[posisi1].equals("128") && strSpinner2[posisi2].equals("128")) {
            Word_Size = 64;
            Keywords = 2;
            Const_Seq = 2;
            Rounds = 68;
            tempCost = "Z2";
//            c = 0xfffffffffffffffc;
//            f = 0xffffffffffffffff;
//            editText1.setText(Word_Size);
//            editText2.setText(Const_Seq);
//            editText3.setText(Keywords);
//            editText14.setText(Rounds);
        } else if (strSpinner1[posisi1].equals("128") && strSpinner2[posisi2].equals("192")) {
            Word_Size = 64;
            Keywords = 3;
            Const_Seq = 3;
            Rounds = 69;
            tempCost = "Z3";
//            c = 0xfffffffffffffffc;
//            f = 0xffffffffffffffff;
//            editText1.setText(Word_Size);
//            editText2.setText(Const_Seq);
//            editText3.setText(Keywords);
//            editText14.setText(Rounds);
        } else if (strSpinner1[posisi1].equals("128") && strSpinner2[posisi2].equals("256")) {
            Word_Size = 64;
            Keywords = 4;
            Const_Seq = 4;
            Rounds = 72;
            tempCost = "Z4";
            //    c = 0xfffffffffffffffc;
//            f = 0xffffffffffffffff;
//            editText1.setText(Word_Size);
//            editText2.setText(Const_Seq);
//            editText3.setText(Keywords);
//            editText14.setText(Rounds);
        } else {
            Word_Size = 0;
            Keywords = 0;
            Const_Seq = 0;
            Rounds = 0;
            tempCost = "none";
            c = 0xfffc;
            f = 0xffff;
//            editText1.setText(Word_Size);
//            editText2.setText(Const_Seq);
//            editText3.setText(Keywords);
//            editText14.setText(Rounds);
        }
        encrypt();
        keyExpansion(String.valueOf(editText4));
    }


    public void pisah2() {
        if (strSpinner1[posisi1].equals("64") && Keywords == 4) {
            System.out.println("Key_Size == 64 && Keywords ==4 " + Key_Size + " dan " + Keywords + " ccc " + Rounds);
        } else if (strSpinner1[posisi1].equals("") && Keywords == 4) {

        } else if (strSpinner1[posisi1].equals("") && Keywords == 4) {

        } else if (strSpinner1[posisi1].equals("") && Keywords == 4) {

        } else if (strSpinner1[posisi1].equals("") && Keywords == 4) {

        } else if (strSpinner1[posisi1].equals("") && Keywords == 4) {

        } else if (strSpinner1[posisi1].equals("") && Keywords == 4) {

        } else if (strSpinner1[posisi1].equals("") && Keywords == 4) {

        } else if (strSpinner1[posisi1].equals("") && Keywords == 4) {

        } else if (strSpinner1[posisi1].equals("") && Keywords == 4) {

        } else if (strSpinner1[posisi1].equals("") && Keywords == 4) {

        } else if (strSpinner1[posisi1].equals("") && Keywords == 4) {

        }
    }

    private void encrypt() {
        int x = 0, y = 0, tmp;
        for (int j = Rounds - 1; j >= 0; j--) {
            tmp = y;
            y = (x ^ (rotateLeft(y, 1, 24) & rotateLeft(y, 8, 24)) ^ rotateLeft(y, 2, 24) ^ key[j]) & f;//y = x XOR ((S^1)y AND (S^8)y) XOR (S^2)y XOR k[i]
            x = tmp;//x = y
        }

    }

    private void decrypt() {
        int x = 0, y = 0, tmp;
        for (int j = Rounds - 1; j >= 0; j--) {
            tmp = y;
            y = (x ^ (rotateLeft(y, 1, 24) & rotateLeft(y, 8, 24)) ^ rotateLeft(y, 2, 24) ^ key[j]) & 0xFFFFFF;//y = x XOR ((S^1)y AND (S^8)y) XOR (S^2)y XOR k[i]
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
