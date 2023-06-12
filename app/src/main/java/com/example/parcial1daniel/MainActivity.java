package com.example.parcial1daniel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bdd.BDHelper;

public class MainActivity extends AppCompatActivity {
    EditText et_funcionario,et_area,et_cargo,et_hijos,et_estado,et_atrasos,et_sueldo,et_subsidio,et_descuentoA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_funcionario=findViewById(R.id.txtFuncionarios);
        et_area=findViewById(R.id.txtArea);
        et_cargo=findViewById(R.id.txtCargo);
        et_hijos=findViewById(R.id.txtHijos);
        et_estado=findViewById(R.id.txtEstado);
        et_atrasos=findViewById(R.id.txtAtrasos);
        et_sueldo=findViewById(R.id.txtSueldo);
        et_subsidio=findViewById(R.id.txtSubsidio);
        et_descuentoA=findViewById(R.id.txtDescuentoA);
    }

    public double determinarSueldo(String cargo){
        double sueldo=0.00;
        //String cargo=et_cargo.getText().toString();
        if (cargo.equals("Administrativo")==true) {
            sueldo=880.00;
        }else if(cargo.equals("Docente")==true){

            sueldo= 1000.00;
        }
        return sueldo;
    }

    public double subsidio(int numero){
        double sub=0.00;

        if(numero>0){
            sub=numero*50;
        }else{
            sub=0;
        }
        return sub;
    }
    public double atrasos(){
        String atraso = et_atrasos.getText().toString();
        double sueldo = Double.parseDouble(et_sueldo.getText().toString());
        boolean tieneAtrasos = atraso.equalsIgnoreCase("sí");

        double descuentoAtrasos = 0.0;
        if (tieneAtrasos) {
            descuentoAtrasos = -0.08 * sueldo;
        }
        return descuentoAtrasos;
    }


    public void registrar(View view){
        BDHelper admin=new BDHelper(this,"parcial1.db",null,1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        String funcionarios=et_funcionario.getText().toString();
        String area=et_area.getText().toString();
        String cargo=et_cargo.getText().toString();
        String hijos=et_hijos.getText().toString();
        String estado=et_estado.getText().toString();


        if(!funcionarios.isEmpty() && !area.isEmpty() && !cargo.isEmpty() && !hijos.isEmpty() && !estado.isEmpty()){
            ContentValues registro=new ContentValues();
            registro.put("usu_funcionario",funcionarios);
            registro.put("usu_area",area);
            registro.put("usu_cargo",cargo);
            registro.put("usu_hijos",hijos);
            registro.put("usu_estado",estado);

            /*double sueldo;
            if (cargo.equals("docente")) {
                sueldo = 1000.0;
            } else if (cargo.equals("administrativo")) {
                sueldo = 850.0;
            } else {
                sueldo = 0.0;
            }

            int numHijos = Integer.parseInt(hijos);
            double subsidioHijos = 50.0 * numHijos;
            //double descuentoAtrasos = -0.08 * sueldo;
            double horasExtras = 12.0;
            String atrasos = et_atrasos.getText().toString();
            boolean tieneAtrasos = atrasos.equalsIgnoreCase("sí");

            double descuentoAtrasos = 0.0;
            if (tieneAtrasos) {
                descuentoAtrasos = -0.08 * sueldo;
            }

            //sueldo = sueldo + subsidioHijos - Math.abs(descuentoAtrasos) + horasExtras;
            sueldo = sueldo + subsidioHijos + descuentoAtrasos + horasExtras;
            TextView tvSueldo = findViewById(R.id.txtSueldo);
            tvSueldo.setText("Sueldo: $" + sueldo);

            registro.put("usu_sueldo", sueldo);*/

            bd.insert("tblFuncionarios",null,registro);
            Toast.makeText(this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();
            et_funcionario.setText("");
            et_area.setText("");
            et_cargo.setText("");
            et_hijos.setText("");
            et_estado.setText("");
            et_sueldo.setText(this.determinarSueldo(cargo)+"");
            int numHijos=Integer.parseInt(hijos);
            et_subsidio.setText(this.subsidio(numHijos)+"");
            et_descuentoA.setText(this.atrasos()+"");
            bd.close();
        } else {
            Toast.makeText(this,"FAVOR INGRESAR TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
        }
    }
}
