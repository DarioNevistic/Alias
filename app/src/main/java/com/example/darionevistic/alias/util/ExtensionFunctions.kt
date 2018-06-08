package com.example.darionevistic.alias.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.io.Serializable
import java.math.BigDecimal
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

/**
 * Created by josip on 19/04/2018.
 */

//region - View
/**
 * Show/Remove the view
 */
fun View.showHide(): View {
    visibility = if (visibility != View.GONE) View.GONE else View.VISIBLE
    return this
}

/**
 * Extension method to show a keyboard for View.
 */
fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

/**
 * Set an onclick listener
 */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }

/**
 * Extension method to set OnClickListener on a view.
 */
fun <T : View> T.longClick(block: (T) -> Boolean) = setOnLongClickListener { block(it as T) }

/**
 * Show the view  (visibility = View.VISIBLE)
 */
fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Hide the view. (visibility = View.INVISIBLE)
 */
fun View.hide(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

/**
 * Remove the view (visibility = View.GONE)
 */
fun View.remove(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}
//endregion

//region - Fragment

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun Fragment.addFragment(fragment: Fragment, frameId: Int, backStack: Boolean = true) {
    if (fragmentManager?.findFragmentByTag(fragment.javaClass.simpleName) == null)
        fragmentManager?.inTransaction {
            add(frameId, fragment).also { if (backStack) let { addToBackStack(fragment.javaClass.simpleName) } }
        }
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int, backStack: Boolean = true) {
    if (fragmentManager?.findFragmentByTag(fragment.javaClass.simpleName) == null)
        fragmentManager?.inTransaction {
            replace(frameId, fragment).also { if (backStack) let { addToBackStack(fragment.javaClass.simpleName) } }
        }
}

fun Fragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) = context?.let { Toast.makeText(it, text, duration).show() }

fun Fragment.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_LONG) = context?.let { Toast.makeText(it, textId, duration).show() }

inline fun <T : Fragment> T.withArguments(vararg params: Pair<String, Any?>): T {
    arguments = bundleOf(*params)
    return this
}

inline fun <T : DialogFragment> T.withArguments(vararg params: Pair<String, Any?>): T {
    arguments = bundleOf(*params)
    return this
}

fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}
//endregion

//region - Activity

/**
 * Extension method to provide hide keyboard for [Activity].
 */
fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(Context
                .INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null)
        supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, backStack: Boolean = true) {
    if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null)
        supportFragmentManager.inTransaction {
            replace(frameId, fragment).also { if (backStack) let { addToBackStack(fragment.javaClass.simpleName) } }
        }
}

fun AppCompatActivity.replaceFragmentWithAnim(fragment: Fragment, frameId: Int, enterAnim: Int, exitAnim: Int, backStack: Boolean = true) {
    if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null)
        supportFragmentManager.inTransaction {
            replace(frameId, fragment, fragment.javaClass.simpleName)
                    .setCustomAnimations(enterAnim, exitAnim)
                    .also { if (backStack) let { addToBackStack(fragment.javaClass.simpleName) } }
        }
}

//region - Context
/**
 * Extension method to show toast for Context.
 */
fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) = this?.let { Toast.makeText(it, text, duration).show() }

/**
 * Extension method to show toast for Context.
 */
fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_LONG) = this?.let { Toast.makeText(it, textId, duration).show() }
//endregion

//region - Collections
inline fun <reified T, Y> MutableList<T>.listOfField(property: KMutableProperty1<T, Y?>): MutableList<Y> {
    val yy = ArrayList<Y>()
    this.forEach { t: T ->
        yy.add(property.get(t) as Y)
    }
    return yy
}

inline fun <reified T, Y> List<T>.listOfField(property: KProperty1<T, Y?>): List<Y> {
    val yy = ArrayList<Y>()
    this.forEach { t: T ->
        yy.add(property.get(t) as Y)
    }
    return yy
}

fun bundleOf(vararg params: Pair<String, Any?>): Bundle {
    val b = Bundle()
    for (p in params) {
        val (k, v) = p
        when (v) {
            null -> b.putSerializable(k, null)
            is Boolean -> b.putBoolean(k, v)
            is Byte -> b.putByte(k, v)
            is Char -> b.putChar(k, v)
            is Short -> b.putShort(k, v)
            is Int -> b.putInt(k, v)
            is Long -> b.putLong(k, v)
            is Float -> b.putFloat(k, v)
            is Double -> b.putDouble(k, v)
            is String -> b.putString(k, v)
            is CharSequence -> b.putCharSequence(k, v)
            is Parcelable -> b.putParcelable(k, v)
            is Serializable -> b.putSerializable(k, v)
            is BooleanArray -> b.putBooleanArray(k, v)
            is ByteArray -> b.putByteArray(k, v)
            is CharArray -> b.putCharArray(k, v)
            is DoubleArray -> b.putDoubleArray(k, v)
            is FloatArray -> b.putFloatArray(k, v)
            is IntArray -> b.putIntArray(k, v)
            is LongArray -> b.putLongArray(k, v)
            is Array<*> -> {
                @Suppress("UNCHECKED_CAST")
                when {
                    v.isArrayOf<Parcelable>() -> b.putParcelableArray(k, v as Array<out Parcelable>)
                    v.isArrayOf<CharSequence>() -> b.putCharSequenceArray(k, v as Array<out CharSequence>)
                    v.isArrayOf<String>() -> b.putStringArray(k, v as Array<out String>)
                    else -> throw RuntimeException("Unsupported bundle component (${v.javaClass})")
                }
            }
            is ShortArray -> b.putShortArray(k, v)
            is Bundle -> b.putBundle(k, v)
            else -> throw RuntimeException("Unsupported bundle component (${v.javaClass})")
        }
    }

    return b
}

//region - Numbers
fun String.roundTo2DecimalPlaces() =
        BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toString()

fun getNumberOfCombinations(selected: Int, maxOf: Int): Int {
    return (maxOf.factorial() / ((maxOf - selected).factorial() * selected.factorial())).toInt()
}

fun Int.factorial() :Long{
    return (1..this).fold(1L) { a, n -> a * n }
}
