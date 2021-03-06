package com.map.woodlands.woodlandsmap.Data;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.map.woodlands.woodlandsmap.R;

/**
 * Created by Jimmy on 3/16/2015.
 * Used for showing or hiding views in FormActivity
 */
public class ViewToggler {
    private AdapterView<?> mParent;
    private int mPosition;
    private LinearLayout culvertBlock;
    private LinearLayout bridgeBlock;
    private LinearLayout erosionBlock;
    private LinearLayout fishSamplingBlock;
    private LinearLayout blockageBlock;
    private LinearLayout culvertD2Block;
    private LinearLayout culvertD3Block;
    private LinearLayout fishReasonBlock;
    private LinearLayout scourBlock;
    private LinearLayout outletBlock;
    private LinearLayout substrateBlock;
    private LinearLayout streamBlock;

    private View loadingView;

    public ViewToggler(AdapterView<?> parent, int position, LinearLayout... blocks){
        this.mParent = parent;
        this.mPosition = position;
        if(blocks.length>5){
            this.culvertBlock = blocks[0];
            this.bridgeBlock = blocks[1];
            this.erosionBlock = blocks[2];
            this.fishSamplingBlock = blocks[3];
            this.blockageBlock = blocks[4];
            this.culvertD2Block = blocks[5];
            this.culvertD3Block = blocks[6];
            this.fishReasonBlock = blocks[7];
            this.scourBlock = blocks[8];
            this.outletBlock = blocks[9];
            this.substrateBlock = blocks[10];
            this.streamBlock = blocks[11];
        }
    }

    public ViewToggler(View v){
        this.loadingView = v;

    }

    public void toggleView(){
        String s = mParent.getItemAtPosition(mPosition).toString().toLowerCase();
//        Log.i("debug",s);
        switch (mParent.getId()){
            case R.id.crossingTypeDropdown:

                if(s.length() == 0){
                    culvertBlock.setVisibility(View.GONE);
                    bridgeBlock.setVisibility(View.GONE);
                }else if(s.contains("bridge")){
                    culvertBlock.setVisibility(View.GONE);
                    bridgeBlock.setVisibility(View.VISIBLE);
                }else if(s.contains("culvert") && s.contains("single")){
                    culvertBlock.setVisibility(View.VISIBLE);
                    bridgeBlock.setVisibility(View.GONE);
                    culvertD2Block.setVisibility(View.GONE);
                    culvertD3Block.setVisibility(View.GONE);
                }
                else{
                    culvertBlock.setVisibility(View.VISIBLE);
                    bridgeBlock.setVisibility(View.GONE);
                    culvertD2Block.setVisibility(View.VISIBLE);
                    culvertD3Block.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.erosionDropdown:
                if(s.equals("yes") || s.equals("potential")){
                    erosionBlock.setVisibility(View.VISIBLE);
                    LinearLayout sourceLayout = (LinearLayout)erosionBlock.getChildAt(1);
                    TextView starView = (TextView) sourceLayout.getChildAt(1);
                    if(s.equals("yes")){
                        starView.setText("*");
                    }else if(s.equals("potential")){
                        starView.setText("");
                    }

                }else{
                    erosionBlock.setVisibility(View.GONE);
                }
                break;

            case R.id.fishSamplingDropdown:
                if(s.equals("yes")){
                    fishSamplingBlock.setVisibility(View.VISIBLE);
                }else{
                    fishSamplingBlock.setVisibility(View.GONE);
                }
                break;

            case R.id.blockageDropdown:
                if(s.equals("yes")){
                    blockageBlock.setVisibility(View.VISIBLE);
                }else{
                    blockageBlock.setVisibility(View.GONE);
                }
                break;

            case R.id.fishPassageConcernsDropdown:
                if(s.equals("no concerns") || s.equals("")){
                    fishReasonBlock.setVisibility(View.GONE);
                }else{
                    fishReasonBlock.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.scourPoolPresentDropdown:
                if(s.equals("yes")){
                    scourBlock.setVisibility(View.VISIBLE);
                }else{
                    scourBlock.setVisibility(View.GONE);
                }
                break;

            case R.id.culvertOutletTypeDropdown:
                if(s.equals("hanging")){
                    outletBlock.setVisibility(View.VISIBLE);
                }else{
                    outletBlock.setVisibility(View.GONE);
                }
                break;

            case R.id.culvertSubstrateDropdown:
                if(s.equals("yes") || s.equals("potential")){
                    substrateBlock.setVisibility(View.VISIBLE);
                }else{
                    substrateBlock.setVisibility(View.GONE);
                }
                break;

            case R.id.streamClassDropdown:
                if(s.length() == 0 || s.contains("ephemeral")){
                    streamBlock.setVisibility(View.GONE);
                }else{
                    streamBlock.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

    public void toggleLoadingView(){
        if(loadingView.getVisibility() == View.VISIBLE){
            loadingView.setVisibility(View.GONE);
        }else{
            loadingView.setVisibility(View.VISIBLE);
        }
    }
}
