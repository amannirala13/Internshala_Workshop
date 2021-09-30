package com.internshala.utils

import android.content.Context
import com.internshala.workshop.utils.AuthPreferenceConstants
import com.internshala.workshop.utils.FirstRunPreferenceConstants

/**
 * This function checks if the application is being executed for the first time.
 * This function uses shared preferences to store the value.
 *
 *
 * @param context - The context of the activity it's being called from
 * @return [Boolean] - 'true' if its the first run else 'false'
 */
fun isFirstRun(context: Context): Boolean{
    val sharedPreferences = context.getSharedPreferences(
        FirstRunPreferenceConstants.FIRST_RUN_PREFERENCE_MANAGER,
        Context.MODE_PRIVATE)
    return if(sharedPreferences.getBoolean(FirstRunPreferenceConstants.IS_FIRST_RUN, true)){
        sharedPreferences.edit().putBoolean(FirstRunPreferenceConstants.IS_FIRST_RUN, false).apply()
        true
    }else{
        false
    }
}

/**
 * This function checks if the user is authenticated or not.
 *
 *
 * @param context - The context of the activity it's being called from
 * @return [Boolean] - 'true' if the user is logged in else 'false'
 */
fun isValidUser(context: Context): Boolean{
    val sharedPreferences = context.getSharedPreferences(
        AuthPreferenceConstants.AUTH_PREFERENCE_MANAGER,
        Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(AuthPreferenceConstants.AUTH_IS_LOGGED_IN, false)
}

/**
 * This function saves user login information
 *
 * @param context [Context] - The activity context
 * @param userID [Int] - The user id from database
 *
 * @return [Unit]
 */
fun loginUser(context: Context, userID: Int){
    val sharedPreferences = context.getSharedPreferences(
        AuthPreferenceConstants.AUTH_PREFERENCE_MANAGER,
        Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean(AuthPreferenceConstants.AUTH_IS_LOGGED_IN, true).apply()
    sharedPreferences.edit().putInt(AuthPreferenceConstants.AUTH_USER_ID, userID).apply()
}

/**
 * This function logs users out and deletes their information
 *
 * @param context [Context] - The activity context
 *
 * @return [Unit]
 */
fun logoutUser(context:Context){
    val sharedPreferences = context.getSharedPreferences(
        AuthPreferenceConstants.AUTH_PREFERENCE_MANAGER,
        Context.MODE_PRIVATE)
    sharedPreferences.edit().remove(AuthPreferenceConstants.AUTH_IS_LOGGED_IN).apply()
    sharedPreferences.edit().remove(AuthPreferenceConstants.AUTH_USER_ID).apply()
}

/**
 * This function returns user id of the current logged in user
 * The user id is a positive integer.
 *
 * It returns a negative integer if the user is not logged in or null
 *
 * @param context [Context] - Context
 *
 * @return [Int] - User_id
 */
fun getUserID(context: Context):Int{
    val sharedPreferences = context.getSharedPreferences(
        AuthPreferenceConstants.AUTH_PREFERENCE_MANAGER,
        Context.MODE_PRIVATE)
    return sharedPreferences.getInt(AuthPreferenceConstants.AUTH_USER_ID, -1)
}