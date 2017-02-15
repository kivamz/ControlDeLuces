package com.kmzwebdesign.controldeluces;

import android.content.Intent;
import android.os.Bundle;
import com.kmzwebdesign.controldeluces.fragments.InicioFragment;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

public class MainActivity extends MaterialNavigationDrawer{


    @Override
    public void init(Bundle savedInstanceState) {
        setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_FIRST);

        addSection(newSection("Inicio", R.drawable.ic_arrow_drop_down_white_24dp, InicioFragment.newInstance()));//Inicio
        addDivisor();
        addBottomSection(newSection("Ajustes", R.drawable.ic_setting, new Intent(this, SettingActivity.class)));
    }

}
