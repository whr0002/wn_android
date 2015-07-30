package com.map.woodlands.woodlandsmap.Data;

import java.util.ArrayList;

/**
 * Created by Jimmy on 3/16/2015.
 * Used for validating a form
 */
public class FormValidator {
    private Form mForm;
    private ArrayList<String> messages;
    public FormValidator(Form form){
        this.mForm = form;
        this.messages = new ArrayList<String>();
    }


    public void validateForm(){
        if (mForm != null){
            // Date is required
            if(mForm.INSP_DATE.length() == 0){
                messages.add("Date is required");
            }

            if(mForm.INSP_CREW.trim().length() == 0){
                messages.add("Inspection Crew is required");
            }else if(mForm.INSP_CREW.length() > 40){
                messages.add(generateLengthMessage("Inspection Crew", 40));
            }

            if(mForm.INSP_CREW.trim().length()>0 && !mForm.INSP_CREW.matches("^[[a-zA-Z]+\\s*]+$")){
                messages.add("Inspection Crew must contain letters only");
            }

            if(mForm.ACCESS.length() == 0){
                messages.add("Access is required");
            }else if(mForm.ACCESS.length() > 10){
                messages.add(generateLengthMessage("Access", 10));
            }

            if(mForm.CROSS_NM.length() > 20){
                messages.add(generateLengthMessage("Crossing Number", 20));
            }

            if(mForm.CROSS_ID.length() > 20){
                messages.add(generateLengthMessage("Crossing ID", 20));
            }

            if(mForm.STR_ID.length() > 20){
                messages.add(generateLengthMessage("Stream ID", 20));
            }

            if(mForm.DISPOSITION_ID.length() > 20){
                messages.add(generateLengthMessage("Disposition ID", 20));
            }

            if(mForm.LAT.length()> 0 && !isNumeric(mForm.LAT)){
                messages.add(generateNumericMessage("Latitude"));
            }

            if(mForm.LONG.length()> 0 && !isNumeric(mForm.LONG)){
                messages.add(generateNumericMessage("Longitude"));
            }

            if(mForm.STR_CLASS.length() == 0){
                messages.add("Stream Classification is required");
            }

            if(mForm.STR_CLASS.length() > 100){
                messages.add(generateLengthMessage("Stream Classification", 100));
            }

            if(mForm.STR_WIDTH.length()> 0 && !isNumeric(mForm.STR_WIDTH)){
                messages.add(generateNumericMessage("Stream Width"));
            }

            String s = generateRangeMessage("Stream Width", mForm.STR_WIDTH, 0, 100);
            if(s != null){
                messages.add(s);
            }

            if(mForm.CROSS_TYPE.length() == 0){
                messages.add("Crossing Type is required");
            }else{
                // Crossing type is not bridge
                if(!mForm.CROSS_TYPE.toLowerCase().contains("bridge")){

                    // Culvert Length
                    if(mForm.CULV_LEN.length() == 0){
                        messages.add("Culvert Length is required");
                    }else{
                        if(mForm.CULV_LEN.length()> 0 && !isNumeric(mForm.CULV_LEN)){
                            messages.add(generateNumericMessage("Culvert Length"));
                        }

                        s = generateRangeMessage("Culvert Length", mForm.CULV_LEN, 0, 100);
                        if(s != null){
                            messages.add(s);
                        }
                    }

                    // Culvert Back Water Proportion
                    if(mForm.CULV_BACKWATERPROPORTION.length() == 0){
                        messages.add("Culvert Back Water Proportion is required");
                    }

                    // Culvert Slope
                    if(mForm.CULV_SLOPE.length() == 0){
                        messages.add("Culvert Slope is required");
                    }

                    // Culvert Diameter 1
                    if(mForm.CULV_DIA_1.length() == 0){
                        messages.add("Culvert Diameter 1 is required");
                    }

                    if(mForm.CULV_DIA_1.length()> 0 && !isNumeric(mForm.CULV_DIA_1)){
                        messages.add(generateNumericMessage("Culvert Diameter 1-Primary"));
                    }

                    if(mForm.CULV_DIA_2.length()> 0 && !isNumeric(mForm.CULV_DIA_2)){
                        messages.add(generateNumericMessage("Culvert Diameter 2-Secondary"));
                    }

                    if(mForm.CULV_DIA_3.length()> 0 && !isNumeric(mForm.CULV_DIA_3)){
                        messages.add(generateNumericMessage("Culvert Diameter 3-Tertiary"));
                    }

                    s = generateRangeMessage("Culvert Diameter 1-Primary", mForm.CULV_DIA_1, 0, 5000);
                    if(s != null){
                        messages.add(s);
                    }

                    s = generateRangeMessage("Culvert Diameter 2-Secondary", mForm.CULV_DIA_2, 0, 5000);
                    if(s != null){
                        messages.add(s);
                    }

                    s = generateRangeMessage("Culvert Diameter 3-Tertiary", mForm.CULV_DIA_3, 0, 5000);
                    if(s != null){
                        messages.add(s);
                    }

                    // Culvert Substrate
                    if(mForm.CULV_SUBS.length() == 0){
                        messages.add("Culvert Substrate is required");
                    }else if(mForm.CULV_SUBS.equals("Yes") || mForm.CULV_SUBS.equals("Potential")){
                        if(mForm.CULV_SUBSTYPE1.length() == 0 || mForm.CULV_SUBSPROPORTION1.length() == 0){
                            messages.add("Substrate Primary is required");
                        }
                    }



                    // Road Fill Above Culvert
                    if(mForm.RoadFillAboveCulvert.length() == 0){
                        messages.add("Road Fill above Culvert is required");
                    }

                    // Outlet Type
                    if(mForm.CULV_OUTLETTYPE.length() == 0){
                        messages.add("Culvert Outlet Type is required");
                    }else if(mForm.CULV_OUTLETTYPE.equals("Hanging")){
                        if(mForm.CULV_OPGAP.length() == 0){
                            messages.add("Culvert Outlet Gap is required");
                        }else{
                            if(mForm.CULV_OPGAP.length()> 0 && !isNumeric(mForm.CULV_OPGAP)){
                                messages.add(generateNumericMessage("Culvert Outlet Gap"));
                            }

                            s = generateRangeMessage("Culvert Outlet Gap", mForm.CULV_OPGAP, 0, 1000);
                            if(s != null){
                                messages.add(s);
                            }
                        }
                    }

                    // Scour Pool Present
                    if(mForm.SCOUR_POOL.length() == 0){
                        messages.add("Scour Pool Present is required");
                    }else{
                        if(mForm.SCOUR_POOL.equals("Yes")){

                            if(mForm.CULV_OPOOD.length() == 0){
                                messages.add("Culvert Pool Depth is required");
                            }else{
                                if(mForm.CULV_OPOOD.length()> 0 && !isNumeric(mForm.CULV_OPOOD)){
                                    messages.add(generateNumericMessage("Culvert Pool Depth"));
                                }
                                s = generateRangeMessage("Culvert Pool Depth", mForm.CULV_OPOOD, 0, 1000);
                                if(s != null){
                                    messages.add(s);
                                }
                            }

                            if(mForm.FirstRiffleDistance.length() == 0){
                                messages.add("First Riffle Distance is required");
                            }
                        }
                    }

                    // Delineator
                    if(mForm.DELINEATOR.length() == 0){
                        messages.add("Delineator is required");
                    }

                }else{
                    // Crossing Type is bridge
                    if(mForm.BRDG_LEN.length()> 0 && !isNumeric(mForm.BRDG_LEN)){
                        messages.add(generateNumericMessage("Bridge Length"));
                    }
                    s = generateRangeMessage("Bridge Length", mForm.BRDG_LEN, 0, 100);
                    if(s != null){
                        messages.add(s);
                    }
                }
            }

            // Erosion
            if(mForm.EROSION.length() == 0){
                messages.add("Erosion is required");
            }else{

                // Erosion is Yes or Pot, validate Erosion Type and Degree
                if(mForm.EROSION.equals("Yes") || mForm.EROSION.equals("Potential")){


                    // Erosion Type
                    if(mForm.EROSION_TY1.length() == 0){
                        messages.add("Erosion Type is required");
                    }

                    // Erosion Source
                    if(mForm.EROSION_SO.length() == 0){
                        messages.add("Erosion Source is required");
                    }

                    // Erosion Extent
                    if(mForm.EROSION_DE.length() == 0){
                        messages.add("Erosion Extent is required");
                    }

                    // Erosion Area
                    if(mForm.EROSION_AR.length()> 0 && !isNumeric(mForm.EROSION_AR)){
                        messages.add(generateNumericMessage("Erosion Area"));
                    }
                    s = generateRangeMessage("Erosion Area", mForm.EROSION_AR, 0, 1000);
                    if(s != null){
                        messages.add(s);
                    }
                }
            }


            if(mForm.FISH_PCONC.length() == 0){
                messages.add("Fish Passage Concerns is required");
            }else if(!mForm.FISH_PCONC.equals("No Concerns")){

                if(mForm.FISH_PCONCREASON.length() == 0) {
                    messages.add("Fish Passage Concerns Reason is required");
                }
            }

            if(mForm.FISH_PCONCREASON.length() > 20){
                messages.add(generateLengthMessage("Fish Passage Concerns Reason", 20));
            }

            if(mForm.BLOCKAGE.length() == 0){
                messages.add("Blockage is required");
            }else if(mForm.BLOCKAGE.equals("Yes")){
                if(mForm.BLOC_MATR.length() == 0){
                    messages.add("Blockage Material is required");
                }

                if(mForm.BLOC_CAUS.length() == 0){
                    messages.add("Blockage Cause is required");
                }
            }

            if(mForm.BLOCKAGE.length() > 50){
                messages.add(generateLengthMessage("Blockage", 50));
            }

            if(mForm.BLOC_MATR.length() > 20){
                messages.add(generateLengthMessage("Blockage Material", 20));
            }

            if(mForm.BLOC_CAUS.length() > 50){
                messages.add(generateLengthMessage("Blockage Cause", 50));
            }

            if(mForm.EMG_REP_RE.length() == 0){
                messages.add("Emergency Repair Required is required");
            }

            if(mForm.STU_PROBS.length() == 0){
                messages.add("Structural Problems is required");
            }

            if(mForm.REMARKS.length() > 200){
                messages.add(generateLengthMessage("Remarks", 200));
            }

            if(mForm.SEDEMENTAT.length() == 0){
                messages.add("Sedimentation is required");
            }





        }else{
            messages.add("Form is null");
        }

        if(messages.size() == 0){
            mForm.STATUS = "Ready to submit";
        }else{
            mForm.STATUS = "Not complete";
        }
        mForm.messages = messages;
    }

    private boolean isNumeric(String str){
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private String generateLengthMessage(String fieldName, int length){
        String s = "The length of " + fieldName +" must be less than " + length + " characters";
        return s;
    }

    private String generateNumericMessage(String fieldName){
        String s = fieldName +" must be a numeric value";
        return s;
    }

    private String generateRangeMessage(String fieldName, String value, int start, int end){
        if(value != null && value.length()>0 && isNumeric(value)){
            double d = Double.parseDouble(value);
            if(d < start || d > end){
                return fieldName+" must be in range "+start+"-"+end;
            }
        }
        return null;
    }
}
