/*
 * ====================================================================
 * Copyright (c) 2012-2023 AbandonedCart.  All rights reserved.
 *
 * https://github.com/AbandonedCart/AbandonedCart/blob/main/LICENSE#L4
 * ====================================================================
 *
 * The license and distribution terms for any publicly available version or
 * derivative of this code cannot be changed.  i.e. this code cannot simply be
 * copied and put under another distribution license
 * [including the GNU Public License.] Content not subject to these terms is
 * subject to to the terms and conditions of the Apache License, Version 2.0.
 */

package com.hiddenramblings.tagmo.eightbit.io

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.*
import android.net.Uri
import android.os.Build
import android.util.Log
import com.heinrichreimersoftware.androidissuereporter.IssueReporterLauncher
import com.hiddenramblings.tagmo.BuildConfig
import com.hiddenramblings.tagmo.Preferences
import com.hiddenramblings.tagmo.R
import com.hiddenramblings.tagmo.TagMo
import java.io.*
import java.util.*

@Suppress("UNUSED")
object Debug {
    private val context: Context
        get() = TagMo.appContext
    private val mPrefs = Preferences(context)

    val isOxygenOS: Boolean
        get() = try {
            @SuppressLint("PrivateApi")
            val c = Class.forName("android.os.SystemProperties")
            val get = c.getMethod("get", String::class.java)
            val name = get.invoke(c, "ro.vendor.oplus.market.name") as String
            name.isNotEmpty()
        } catch (e: Exception) {
            Build.MANUFACTURER == "OnePlus"
        }

    private fun hasDebugging(): Boolean {
        return !mPrefs.disableDebug()
    }

    fun getExceptionCause(e: Exception): String? {
        val description =  when {
            null != e.message -> e.message
            null != e.cause -> e.cause.toString()
            else -> null
        }
        return if (null != description && description.contains(" : "))
            description.substring(description.indexOf(":") + 2)
        else description
    }
    
    fun getExceptionClass(e: Exception) : String {
        return e.cause?.javaClass?.name ?: e.javaClass.name
    }

    fun hasException(e: Exception, target: String): Boolean {
        if (e.stackTrace.isNullOrEmpty()) return false
        for (trace in e.stackTrace) {
            if (null != trace && ("${trace.className}.${trace.methodName}") == target) return true
        }
        return false
    }

    @JvmStatic
    fun error(source: Class<*>, params: String?) {
        if (hasDebugging()) params?.let { Log.e(source.simpleName, it) }
    }

    @JvmStatic
    fun error(source: Class<*>, resource: Int) {
        error(source, context.getString(resource))
    }

    @JvmStatic
    fun error(source: Class<*>, resource: Int, params: String?) {
        error(source, context.getString(resource, params))
    }

    @JvmStatic
    fun error(ex: Exception) {
        if (!hasDebugging()) return
        if (ex.stackTrace.isNotEmpty()) {
            val exception = StringWriter()
            ex.printStackTrace(PrintWriter(exception))
            error(ex.javaClass, exception.toString())
        }
    }

    @JvmStatic
    fun error(resource: Int, ex: Exception) {
        if (hasDebugging()) Log.e(ex.javaClass.simpleName, context.getString(resource), ex)
    }

    @JvmStatic
    fun warn(source: Class<*>, params: String?) {
        if (hasDebugging()) params?.let { Log.w(source.simpleName, it) }
    }

    @JvmStatic
    fun warn(source: Class<*>, resource: Int) {
        warn(source, context.getString(resource))
    }

    @JvmStatic
    fun warn(source: Class<*>, resource: Int, params: String?) {
        warn(source, context.getString(resource, params))
    }

    @JvmStatic
    fun warn(ex: Exception) {
        if (!hasDebugging()) return
        if (ex.stackTrace.isNotEmpty()) {
            val exception = StringWriter()
            ex.printStackTrace(PrintWriter(exception))
            warn(ex.javaClass, exception.toString())
        }
    }

    @JvmStatic
    fun warn(resource: Int, ex: Exception) {
        if (hasDebugging()) Log.w(ex.javaClass.simpleName, context.getString(resource), ex)
    }

    @JvmStatic
    fun info(source: Class<*>, params: String?) {
        if (hasDebugging()) params?.let { Log.i(source.simpleName, it) }
    }

    @JvmStatic
    fun info(source: Class<*>, resource: Int) {
        info(source, context.getString(resource))
    }

    @JvmStatic
    fun info(source: Class<*>, resource: Int, params: String?) {
        info(source, context.getString(resource, params))
    }

    @JvmStatic
    fun info(ex: Exception) {
        if (!hasDebugging()) return
        if (ex.stackTrace.isNotEmpty()) {
            val exception = StringWriter()
            ex.printStackTrace(PrintWriter(exception))
            info(ex.javaClass, exception.toString())
        }
    }

    @JvmStatic
    fun info(resource: Int, ex: Exception) {
        if (hasDebugging()) Log.i(ex.javaClass.simpleName, context.getString(resource), ex)
    }

    @JvmStatic
    fun verbose(source: Class<*>, params: String?) {
        if (BuildConfig.DEBUG && hasDebugging()) params?.let { Log.d(source.simpleName, it) }
    }

    @JvmStatic
    fun verbose(source: Class<*>, resource: Int) {
        verbose(source, context.getString(resource))
    }

    @JvmStatic
    fun verbose(source: Class<*>, resource: Int, params: String?) {
        verbose(source, context.getString(resource, params))
    }

    @JvmStatic
    fun verbose(ex: Exception) {
        if (!hasDebugging()) return
        if (ex.stackTrace.isNotEmpty()) {
            val exception = StringWriter()
            ex.printStackTrace(PrintWriter(exception))
            verbose(ex.javaClass, exception.toString())
        }
    }

    @JvmStatic
    fun verbose(resource: Int, ex: Exception) {
        if (hasDebugging()) Log.d(ex.javaClass.simpleName, context.getString(resource), ex)
    }

    private const val hex = "6768705f74314953736669344f4c415831537365" +
                            "7167636a4f5a42783641736b6f33314f7650697a"
    private val token: String get() {
        val output = StringBuilder()
        var i = 0
        while (i < hex.length) {
            output.append(hex.substring(i, i + 2).toInt(16).toChar())
            i += 2
        }
        return output.toString()
    }

    private const val issueUrl = ("https://github.com/HiddenRamblings/TagMo/issues/new?"
            + "labels=logcat&template=bug_report.yml&title=[Bug]%3A+")

    private val Long.floatForm : String
        get() = String.format(Locale.US, "%.2f", this.toDouble())

    private const val Kb: Long = 1024
    private const val Mb = Kb * 1024
    private const val Gb = Mb * 1024
    private const val Tb = Gb * 1024
    private const val Pb = Tb * 1024
    private const val Eb = Pb * 1024
    private fun bytesToSizeUnit(size: Long): String {
        return when {
            size < Kb -> size.floatForm + " byte"
            size < Mb -> (size / Kb).floatForm + " KB"
            size < Gb -> (size / Mb).floatForm + " MB"
            size < Tb -> (size / Gb).floatForm + " GB"
            size < Pb -> (size / Tb).floatForm + " TB"
            size < Eb -> (size / Pb).floatForm + " Pb"
            else -> (size / Eb).floatForm + " Eb"
        }
    }

    private fun getDeviceRAM(context: Context): String {
        (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).run {
            val memInfo = ActivityManager.MemoryInfo()
            getMemoryInfo(memInfo)
            return bytesToSizeUnit(memInfo.totalMem)
        }
    }

    private fun getDeviceProfile(context: Context): StringBuilder {
        val separator = if (System.getProperty("line.separator") != null)
            Objects.requireNonNull(System.getProperty("line.separator"))
        else "\n"
        val log = StringBuilder(separator)
        log.append(TagMo.getVersionLabel(true))
        log.append(separator)
        log.append("Android ")
        var codeName = "UNKNOWN"
        for (field in Build.VERSION_CODES::class.java.fields) {
            try {
                if (field.getInt(Build.VERSION_CODES::class.java) == Build.VERSION.SDK_INT)
                    codeName = field.name
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
        log.append(codeName)
        log.append(" (")
        log.append(Build.VERSION.RELEASE)
        log.append(") - ").append(getDeviceRAM(context)).append(" RAM")
        return log
    }

    private fun setEmailParams(action: String, subject: String, text: String): Intent {
        return Intent(action).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("tagmo.git@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        }
    }

    private fun submitLogcat(context: Context, logText: String) {
        if (BuildConfig.WEAR_OS) return
        val subject = context.getString(R.string.git_issue_title, BuildConfig.COMMIT)
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
            setPrimaryClip(ClipData.newPlainText(subject, logText))
        }
        try {
            val emailIntent = setEmailParams(Intent.ACTION_SENDTO, subject, logText)
            context.startActivity(Intent.createChooser(
                emailIntent, context.getString(R.string.logcat_crash)
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } catch (anf: ActivityNotFoundException) {
            try {
                val emailIntent = setEmailParams(Intent.ACTION_SEND, subject, logText)
                context.startActivity(Intent.createChooser(
                    emailIntent, context.getString(R.string.logcat_crash)
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            } catch (ex: ActivityNotFoundException) {
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(issueUrl)))
                } catch (ignored: Exception) { }
            }
        }
    }

    @JvmStatic
    fun processException(context: Context, exception: String?) {
        val separator = if (System.getProperty("line.separator") != null) Objects.requireNonNull(
            System.getProperty("line.separator")
        ) else "\n"
        val log = getDeviceProfile(context)
        log.append(separator).append(separator).append(exception)
        submitLogcat(context, log.toString())
    }

    @JvmStatic
    @Throws(IOException::class)
    fun processLogcat(context: Context): Boolean {
        val project = context.getString(R.string.tagmo)
        val username = "HiddenRamblings"
        val separator = if (System.getProperty("line.separator") != null) Objects.requireNonNull(
            System.getProperty("line.separator")
        ) else "\n"
        val log = getDeviceProfile(context)
        val mLogcatProc = Runtime.getRuntime().exec(
            arrayOf(
                "logcat", "-d", "-t", "192", BuildConfig.APPLICATION_ID,
                "AndroidRuntime", "System.err",
                "ViewRootImpl*:S", "IssueReporterActivity:S", "*:W"
            )
        )
        val reader = BufferedReader(InputStreamReader(mLogcatProc.inputStream))
        log.append(separator).append(separator)
        var line: String?
        while (null != reader.readLine().also { line = it }) {
            log.append(line).append(separator)
        }
        reader.close()
        val logText = log.toString()
        if (!logText.contains("AndroidRuntime")) {
            submitLogcat(context, logText)
            return false
        }
        return try {
            IssueReporterLauncher.forTarget(username, project)
                .theme(R.style.AppTheme_NoActionBar)
                .guestToken(token)
                .guestEmailRequired(false)
                .publicIssueUrl(issueUrl)
                .titleTextDefault(context.getString(R.string.git_issue_title, BuildConfig.COMMIT))
                .minDescriptionLength(1)
                .putExtraInfo("logcat", logText)
                .homeAsUpEnabled(false).launch(context)
            true
        } catch (ignored: Exception) {
            submitLogcat(context, logText)
            true
        }
    }
}