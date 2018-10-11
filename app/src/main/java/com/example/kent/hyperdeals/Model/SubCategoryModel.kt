package com.example.kent.hyperdeals.Model

class SubCategoryModel(var subCategoryName: String, var subsubCategoryList: ArrayList<SubsubCategoryModel>)

{
    class SubsubCategoryModel(var SubsubcategoryName:String,var isChecked:Boolean=false)
}