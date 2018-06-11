package com.example.a0603614.udacity_baking.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    public double quantity;
    public String measure;
    public String ingredient;


    public static Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public Ingredient() {}

    public Ingredient(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
}
