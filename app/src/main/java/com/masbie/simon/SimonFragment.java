package com.masbie.simon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText editWord, editConsts, editKeyWord, editKey, editKey0, editKey1, editKey2, editKey3, editPlainT, editUp,
            editDown, editUp1, editDown1, editRound, editCipherT;
    Button buttonEncrypt, buttonDecrypt;
    TextView textCekEncrypt, textCekDecrypt;
    int posisi1 = 0, posisi2 = 0;
    LinearLayout linEnc, linDec;
    ListView listEnc, listDec;
    ArrayAdapter<String> adapter;

    // Inisialisai Variabel Global
    int blockSize = 32;
    int keySize = 64;
    int wordSize = 16;
    int keyWords = 4;
    int zSeq = 0;
    int rounds = 32;
    String keyT1, keyT2;

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
        getActivity().setTitle("Simon");
        spinner1 = (MaterialSpinner) view.findViewById(R.id.spinner);
        spinner2 = (MaterialSpinner) view.findViewById(R.id.spinner2);
        editWord = (EditText) view.findViewById(R.id.word);
        editConsts = (EditText) view.findViewById(R.id.consts);
        editKeyWord = (EditText) view.findViewById(R.id.keyword);
        editRound = (EditText) view.findViewById(R.id.round);
        editKey = (EditText) view.findViewById(R.id.key);
        editKey0 = (EditText) view.findViewById(R.id.Key0);
        editKey1 = (EditText) view.findViewById(R.id.Key1);
        editKey2 = (EditText) view.findViewById(R.id.Key2);
        editKey3 = (EditText) view.findViewById(R.id.Key3);
        editPlainT = (EditText) view.findViewById(R.id.plaint);
        editUp = (EditText) view.findViewById(R.id.up);
        editDown = (EditText) view.findViewById(R.id.down);
        editCipherT = (EditText) view.findViewById(R.id.cipher);
        editUp1 = (EditText) view.findViewById(R.id.up1);
        editDown1 = (EditText) view.findViewById(R.id.down1);
        textCekEncrypt = (TextView) view.findViewById(R.id.cekEncrypt);
        textCekDecrypt = (TextView) view.findViewById(R.id.cekDecrypt);
        buttonEncrypt = (Button) view.findViewById(R.id.btEncrypt);
        buttonDecrypt = (Button) view.findViewById(R.id.btDecrypt);
        linEnc = (LinearLayout) view.findViewById(R.id.linEncWaktu);
        linDec = (LinearLayout) view.findViewById(R.id.lindecWaktu);
        listEnc = (ListView) view.findViewById(R.id.listViewEnc);
        listDec = (ListView) view.findViewById(R.id.listViewDec);
      //  adapter = new ArrayAdapter<String>(getActivity(), R.id.listViewEnc, "i");
        System.out.println("APalagi sayap");
        String[][] vector = new String[10][2];
        //Simon32/64
        vector[0][0] = "1918111009080100"; //key
        vector[0][1] = "65656877"; //plaintext
        //Ciphertext:c69be9bb

        //Simon48/72
        vector[1][0] = "1211100a0908020100"; //key
        vector[1][1] = "6120676e696c"; //plaintext
        //Ciphertext:dae5ac292cac

        //Simon48/96
        vector[2][0] = "1a19181211100a0908020100"; //key
        vector[2][1] = "72696320646e"; //plaintext
        //Ciphertext:6e06a5acf156

        //Simon64/96
        vector[3][0] = "131211100b0a090803020100"; //key
        vector[3][1] = "6f7220676e696c63"; //plaintext
        //Ciphertext:5ca2e27f111a8fc8

        //Simon64/128
        vector[4][0] = "1b1a1918131211100b0a090803020100"; //key
        vector[4][1] = "656b696c20646e75"; //plaintext
        //Ciphertext:44c8fc20b9dfa07a

        //Simon96/96
        vector[5][0] = "0d0c0b0a0908050403020100"; //key
        vector[5][1] = "2072616c6c69702065687420"; //plaintext
        //Ciphertext:602807a462b469063d8ff082

        //Simon96/144
        vector[6][0] = "1514131211100d0c0b0a0908050403020100"; //key
        vector[6][1] = "74616874207473756420666f"; //plaintext
        //Ciphertext:ecad1c6c451e3f59c5db1ae9

        //Simon128/128
        vector[7][0] = "0f0e0d0c0b0a09080706050403020100"; //key
        vector[7][1] = "63736564207372656c6c657661727420"; //plaintext
        //Ciphertext:49681b1e1e54fe3f65aa832af84e0bbc

        //Simon128/192
        vector[8][0] = "17161514131211100f0e0d0c0b0a09080706050403020100"; //key
        vector[8][1] = "206572656874206e6568772065626972"; //plaintext
        //Ciphertext:c4ac61effcdc0d4f6c9c8d6e2597b85b

        //Simon128/256
        vector[9][0] = "1f1e1d1c1b1a191817161514131211100f0e0d0c0b0a09080706050403020100"; //key
        vector[9][1] = "74206e69206d6f6f6d69732061207369"; //plaintext
        //Ciphertext:8d2b5579afc8a3a03bf72a87efe7b868

//Hasil inputan dari key dan plainText
        editKey.setText(vector[4][0]);
        editPlainT.setText(vector[4][1]);
//Spiner pemilihan nilai block size
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
//Spiner pemilihan nilai keysize
        strSpinner2 = new String[]{
                "0", "64", "72", "96", "128", "144", "192", "256"
        };
        spinner2.setItems(strSpinner2);
        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // posisi = position;
                posisi2 = position;
                Snackbar.make(view, "Nilai " + strSpinner2[position], Snackbar.LENGTH_LONG).show();
                //Menjalankan method pisah()
                pisah();

            }
        });
        spinner2.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();

            }
        });
//Saat button encrypt di klik, maka
        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strSpinner1[posisi1].equals("") || strSpinner1[posisi1].equals("0") || strSpinner1[posisi1] == null
                        || strSpinner2[posisi2].equals("") || strSpinner2[posisi2].equals("0") || strSpinner2[posisi2] == null
                        || editKey.getText().toString().equals("") || editKey.getText().toString().equals("0") || editKey.getText().toString() == null
                        || editPlainT.getText().toString().equals("") || editPlainT.getText().toString().equals("0") || editPlainT.getText().toString() == null
                        ) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Warning..!!!").setMessage("Pastikan kalau Bloksize, Keysize, Key dan PlaintText sudah terisi dengan benar")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert1 = alert.create();
                    alert1.show();
                } else {
                    keyT1 = editKey.getText().toString();
//                Toast.makeText(getActivity(), "sss " + keyT1, Toast.LENGTH_SHORT).show();
                    //Logika untuk memilih keyExpansion
                    if (strSpinner1[posisi1].equals("32") || strSpinner1[posisi1].equals("48") || strSpinner1[posisi1].equals("64")) {
//                    System.out.println("sss" + keyT1);
//                    Toast.makeText(getActivity(), "sss " + keyT1, Toast.LENGTH_SHORT).show();
                        key1 = keyExpansion1(keyT1);
//                    encrypt();
                    } else if (strSpinner1[posisi1].equals("96") || strSpinner1[posisi1].equals("128")) {
                        key2 = keyExpansion2(keyT1);
//                    encrypt();
                    }
                    encrypt();
                    System.out.println("aaaa");
                }
            }
        });
        //Saat button decrypt di klik,
        buttonDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strSpinner1[posisi1].equals("") || strSpinner1[posisi1].equals("0") || strSpinner1[posisi1] == null
                        || strSpinner2[posisi2].equals("") || strSpinner2[posisi2].equals("0") || strSpinner2[posisi2] == null
                        || editKey.getText().toString().equals("") || editKey.getText().toString().equals("0") || editKey.getText().toString() == null
                        || editCipherT.getText().toString().equals("") || editCipherT.getText().toString().equals("0") || editCipherT.getText().toString() == null
                        ) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Warning..!!!").setMessage("Pastikan kalau Bloksize, Keysize, Key dan CipherText sudah terisi dengan benar")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert1 = alert.create();
                    alert1.show();
                } else {
                    //menjalankan method decrypt
                    decrypt();
                }

            }
        });
        editKey0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key;
                int hexWordSize = wordSize / 4;
                int hexKeySize = keySize / 4;
                int firstIndex = hexKeySize - hexWordSize;
                String[] k = new String[rounds];
                key = editKey.getText().toString();
                //Inisialisasi k[ keyWords - 1]..k[0] /
                for (int i = 0; i < keyWords; i++) {
                    int index = i * hexWordSize;
                    k[i] = key.substring(index, index + hexWordSize);
                }
                if (keyWords == 2) {
                    editKey0.setText(k[0]);
                    editKey1.setText(k[1]);
                    editKey2.setText("none");
                    editKey3.setText("none");
                } else if (keyWords == 3) {
                    editKey0.setText(k[0]);
                    editKey1.setText(k[1]);
                    editKey2.setText(k[2]);
                    editKey3.setText("none");
                } else if (keyWords == 4) {
                    editKey0.setText(k[0]);
                    editKey1.setText(k[1]);
                    editKey2.setText(k[2]);
                    editKey3.setText(k[3]);
                }
                // editKey0.setText("sdsd");
            }
        });
        editUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plain;
                plain = editPlainT.getText().toString();
                int index, temp;
                String[] k = new String[2];
                index = plain.length();
                temp = index / 2;
                k[0] = plain.substring(0, temp);
                k[1] = plain.substring(temp, (index - temp) + temp);
                editUp.setText(k[0]);
                editDown.setText(k[1]);
            }
        });
        editUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hasil;
                hasil = editCipherT.getText().toString();
                int index, temp;
                String[] k = new String[2];
                index = hasil.length();
                temp = index / 2;
                k[0] = hasil.substring(0, temp);
                k[1] = hasil.substring(temp, (index - temp) + temp);
                editUp1.setText(k[0]);
                editDown1.setText(k[1]);
            }
        });
    }


    public void pisah() {
        Toast.makeText(getActivity(), "pisah() ", Toast.LENGTH_SHORT).show();
        //Mengambil data bloksiz dan keySize dari kedua spinner
        blockSize = Integer.parseInt(strSpinner1[posisi1]);
        keySize = Integer.parseInt(strSpinner2[posisi2]);
        System.out.print("dor: ");
        System.out.println(blockSize + keySize);
        System.out.println("keySize apa");
        System.out.println("keySize " + strSpinner1[posisi1]);
        //Logika untuk mengambil nila wordSize, keyWord dll sesuai dengan blokSize dan keySize
        if (strSpinner1[posisi1].equals("32") && strSpinner2[posisi2].equals("64")) {
            wordSize = 16;
            keyWords = 4;
            zSeq = 0;
            rounds = 32;
            tempConst = "Z0";
            cInt = 0xfffc;
            fInt = 0xffff;
        } else if (strSpinner1[posisi1].equals("48") && strSpinner2[posisi2].equals("72")) {
            wordSize = 24;
            keyWords = 3;
            zSeq = 0;
            rounds = 36;
            tempConst = "Z0";
            cInt = 0xfffffc;
            fInt = 0xffffff;
        } else if (strSpinner1[posisi1].equals("48") && strSpinner2[posisi2].equals("96")) {
            wordSize = 24;
            keyWords = 4;
            zSeq = 1;
            rounds = 36;
            tempConst = "Z1";
            cInt = 0xfffffc;
            fInt = 0xffffff;
        } else if (strSpinner1[posisi1].equals("64") && strSpinner2[posisi2].equals("96")) {
            wordSize = 32;
            keyWords = 3;
            zSeq = 2;
            rounds = 42;
            tempConst = "Z2";
            cInt = 0xfffffffc;
            fInt = 0xffffffff;
        } else if (strSpinner1[posisi1].equals("64") && strSpinner2[posisi2].equals("128")) {
            wordSize = 32;
            keyWords = 4;
            zSeq = 3;
            rounds = 44;
            tempConst = "Z3";
            cInt = 0xfffffffc;
            fInt = 0xffffffff;
        } else if (strSpinner1[posisi1].equals("96") && strSpinner2[posisi2].equals("96")) {
            wordSize = 48;
            keyWords = 2;
            zSeq = 2;
            rounds = 52;
            tempConst = "Z2";
            cLong = 0xfffffffffffcl;
            fLong = 0xffffffffffffl;
        } else if (strSpinner1[posisi1].equals("96") && strSpinner2[posisi2].equals("144")) {
            wordSize = 48;
            keyWords = 3;
            zSeq = 3;
            rounds = 54;
            tempConst = "Z3";
            cLong = 0xfffffffffffcl;
            fLong = 0xffffffffffffl;
        } else if (strSpinner1[posisi1].equals("128") && strSpinner2[posisi2].equals("128")) {
            wordSize = 64;
            keyWords = 2;
            zSeq = 2;
            rounds = 68;
            tempConst = "Z2";
            cLong = 0xfffffffffffffffcl;
            fLong = 0xffffffffffffffffl;
        } else if (strSpinner1[posisi1].equals("128") && strSpinner2[posisi2].equals("192")) {
            wordSize = 64;
            keyWords = 3;
            zSeq = 3;
            rounds = 69;
            tempConst = "Z3";
            cLong = 0xfffffffffffffffcl;
            fLong = 0xffffffffffffffffl;
        } else if (strSpinner1[posisi1].equals("128") && strSpinner2[posisi2].equals("256")) {
            wordSize = 64;
            keyWords = 4;
            zSeq = 4;
            rounds = 72;
            tempConst = "Z4";
            cLong = 0xfffffffffffffffcl;
            fLong = 0xffffffffffffffffl;
        } else {
            wordSize = 0;
            keyWords = 0;
            zSeq = 0;
            rounds = 0;
            tempConst = "none";
            cInt = 0xfffc;
            fInt = 0xffff;
        }
        //Menampilkan ke 4 kolom di bawah spinner
        editWord.setText(Integer.toString(wordSize));
        editConsts.setText(tempConst);
        editKeyWord.setText(Integer.toString(keyWords));
        editRound.setText(Integer.toString(rounds));

    }


    private void encrypt() {
        int hexBlockSize = blockSize / 4;
        String cipherText = null;
        if (blockSize == 32 || blockSize == 48 || blockSize == 64) {
            int x = 0, y = 0, tmp;
            x = Integer.parseInt(editPlainT.getText().toString().substring(0, hexBlockSize / 2), 16);
            y = Integer.parseInt(editPlainT.getText().toString().substring(hexBlockSize / 2, hexBlockSize), 16);
            for (int j = 0; j < rounds; j++) {
                tmp = x;
                x = (y ^ (rotateLeft(x, 1, wordSize) & rotateLeft(x, 8, wordSize)) ^ rotateLeft(x, 2, wordSize) ^ key1[j]) & fInt;//x = y XOR ((S^1)x AND (S^8)x) XOR (S^2)x XOR k[i]
                y = tmp;//y = x
            }
            cipherText = String.format("%0" + (hexBlockSize / 2) + "x", x) + String.format("%0" + (hexBlockSize / 2) + "x", y);
        } else if (blockSize == 96 || blockSize == 128) {
            long x = 0, y = 0, tmp;
            x = Long.parseLong(editPlainT.getText().toString().substring(0, hexBlockSize / 2), 16);
            y = Long.parseLong(editPlainT.getText().toString().substring(hexBlockSize / 2, hexBlockSize), 16);
            for (int j = 0; j < rounds; j++) {
                tmp = x;
                x = (y ^ (rotateLeft(x, 1, wordSize) & rotateLeft(x, 8, wordSize)) ^ rotateLeft(x, 2, wordSize) ^ key2[j]) & fLong;//x = y XOR ((S^1)x AND (S^8)x) XOR (S^2)x XOR k[i]
                y = tmp;//y = x
            }
            cipherText = String.format("%0" + (hexBlockSize / 2) + "x", x) + String.format("%0" + (hexBlockSize / 2) + "x", y);
//            textCekEncrypt.setText(String.format("%0" + (hexBlockSize / 2) + "x", x) + String.format("%0" + (hexBlockSize / 2) + "x", y));
//            editCipherT.setText(String.format("%0" + (hexBlockSize / 2) + "x", x) + String.format("%0" + (hexBlockSize / 2) + "x", y));
        }
        textCekEncrypt.setText(cipherText);
        editCipherT.setText(cipherText);
    }

    private void decrypt() {
        int hexBlockSize = blockSize / 4;
        if (blockSize == 32 || blockSize == 48 || blockSize == 64) {
            int x = 0, y = 0, tmp;
            x = Integer.parseInt(editCipherT.getText().toString().substring(0, hexBlockSize / 2), 16);
            y = Integer.parseInt(editCipherT.getText().toString().substring(hexBlockSize / 2, hexBlockSize), 16);
            for (int j = rounds - 1; j >= 0; j--) {
                tmp = y;
                y = (x ^ (rotateLeft(y, 1, wordSize) & rotateLeft(y, 8, wordSize)) ^ rotateLeft(y, 2, wordSize) ^ key1[j]) & fInt;//x = y XOR ((S^1)x AND (S^8)x) XOR (S^2)x XOR k[i]
                x = tmp;//x = y
            }
            textCekDecrypt.setText(String.format("%0" + (hexBlockSize / 2) + "x", x) + String.format("%0" + (hexBlockSize / 2) + "x", y));
//            editCipherT.setText(String.format("%0" + (hexBlockSize / 2) + "x", x) + String.format("%0" + (hexBlockSize / 2) + "x", y));
        } else if (blockSize == 96 || blockSize == 128) {
            long x = 0, y = 0, tmp;
            x = Long.parseLong(editCipherT.getText().toString().substring(0, hexBlockSize / 2), 16);
            y = Long.parseLong(editCipherT.getText().toString().substring(hexBlockSize / 2, hexBlockSize), 16);
            for (int j = rounds - 1; j >= 0; j--) {
                tmp = y;
                y = (x ^ (rotateLeft(y, 1, wordSize) & rotateLeft(y, 8, wordSize)) ^ rotateLeft(y, 2, wordSize) ^ key2[j]) & fLong;//x = y XOR ((S^1)x AND (S^8)x) XOR (S^2)x XOR k[i]
                x = tmp;//x = y
            }
            textCekDecrypt.setText(String.format("%0" + (hexBlockSize / 2) + "x", x) + String.format("%0" + (hexBlockSize / 2) + "x", y));
//            editCipherT.setText(String.format("%0" + (hexBlockSize / 2) + "x", x) + String.format("%0" + (hexBlockSize / 2) + "x", y));
        }
    }

    public int[] keyExpansion1(String key) {
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

    public long[] keyExpansion2(String key) {
        int hexWordSize = wordSize / 4;
        int hexBlockSize = blockSize / 4;
        int hexKeySize = keySize / 4;
        int firstIndex = hexKeySize - hexWordSize;
        long[] k = new long[rounds];
        key = String.format("%1$" + hexKeySize + "s", key).replace(' ', '0');
        /*Inisialisasi k[keyWords-1]..k[0]*/
        for (int i = 0; i < keyWords; i++) {
            int index = firstIndex - (i * hexWordSize);
//            System.out.print("haloo: "+index+" ");
//            System.out.println(index+hexWordSize);
            k[i] = Long.parseLong(key.substring(index, index + hexWordSize), 16) & fLong;
            System.out.println("key round_" + i + ": " + String.format("%0" + hexWordSize + "x", k[i]));

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

    public long rotateRight(long n, int s, int bits) {
        return ((n >>> s) | (n << (bits - s))) & fLong;//Rotasi nilai bit RGB per pixel ke kanan
    }

    public long rotateLeft(long n, int s, int bits) {
        return ((n << s) | (n >>> (bits - s))) & fLong;//Rotasi nilai bit RGB per pixel ke kiri
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View coba = inflater.inflate(R.layout.fragment_simon, container, false);

//        textCekEncrypt = (TextView) coba.findViewById(R.id.cekTulis);
//        textCekEncrypt.setText("ganti ini");
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
