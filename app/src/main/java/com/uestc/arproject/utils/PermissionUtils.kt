package com.uestc.arproject.utils

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import androidx.fragment.app.Fragment


object PermissionUtils {

    const val REQUESR_CODE = 0

    fun getPermission(perssion : String, context : Context) : Boolean{
        val hasPermission = context.checkSelfPermission(perssion)
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            return true
        }

        //没有权限，向用户请求权限
        if (context is Fragment) {
            context.requestPermissions(arrayOf(perssion),REQUESR_CODE)
        }

        if (context is Activity) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(perssion),
                REQUESR_CODE
            )
        }

        return false
    }

    fun getPermissions(perssions : MutableList<String>, context : Context) : Boolean {
        var hasPermission = true
        val noPermissionsList = mutableListOf<String>()
        perssions.forEachIndexed { index, perssion ->
            if (context.checkSelfPermission(perssion) != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false
                noPermissionsList.add(perssion)
            }
        }
        if (hasPermission) return true

        //没有权限，向用户请求权限
        if (context is Fragment) {
            context.requestPermissions(noPermissionsList.toTypedArray(), REQUESR_CODE)
        }

        if (context is Activity) {
            ActivityCompat.requestPermissions(
                context,
                noPermissionsList.toTypedArray(),
                REQUESR_CODE
            )
        }
        return false
    }

}