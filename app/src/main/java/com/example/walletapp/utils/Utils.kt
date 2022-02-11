package com.example.walletapp.utils

import android.annotation.SuppressLint
import android.util.Base64
import java.security.MessageDigest
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Utils {

    companion object {

        private const val encryptionStandard = "AES"
        private const val hashFunction = "SHA-256"
        private const val charset = "UTF-8"
        const val userKey = "zG*qEtkgAIx!"
        const val passKey = "zG*qEtkgAIx!"

        var onBack = 0
        var move = "EXES"

        @SuppressLint("SimpleDateFormat")
        var dfUI = SimpleDateFormat("d MMM yyyy")

        @SuppressLint("SimpleDateFormat")
        var dfDB = SimpleDateFormat("yyyy-MM-dd")

        fun dataUItoDB(data: String): String = try {
            val parse = dfUI.parse(data)
            if (parse != null) dfDB.format(parse)
            else dfDB.format(Date())
        } catch (e: java.lang.Exception) {
            dfDB.format(Date())
        }

        fun dataDBtoUI(data: String): String = try {
            val parse = dfDB.parse(data)
            if (parse != null) dfUI.format(parse)
            else dfUI.format(Date())
        } catch (e: java.lang.Exception) {
            dfUI.format(Date())
        }

        fun decryptPass(pass: String?, key: String): String {
            val secretKeySpec = generateKey(key)
            val cipher = Cipher.getInstance(encryptionStandard)
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
            val datosDecodificados = Base64.decode(pass, Base64.DEFAULT)
            val datosDesencriptadosByte = cipher.doFinal(datosDecodificados)
            return String(datosDesencriptadosByte)
        }

        fun decryptUser(user: String?, key: String): String {
            if (user.isNullOrEmpty()) {
                return ""
            }
            val secretKeySpec = generateKey(key)
            val cipher = Cipher.getInstance(encryptionStandard)
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
            val datosDecodificados = Base64.decode(user, Base64.DEFAULT)
            val datosDesencriptadosByte = cipher.doFinal(datosDecodificados)
            return String(datosDesencriptadosByte)
        }

        fun encryptPass(pass: String, key: String): String {
            val secretKeySpec = generateKey(key)
            val cipher = Cipher.getInstance(encryptionStandard)
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
            val datosEncriptadosBytes = cipher.doFinal(pass.toByteArray())
            return Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT)
        }

        fun encryptUser(user: String, key: String): String {
            val secretKeySpec = generateKey(key)
            val cipher = Cipher.getInstance(encryptionStandard)
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
            val datosEncriptadosBytes = cipher.doFinal(user.toByteArray())
            return Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT)
        }

        private fun generateKey(password: String): SecretKeySpec {
            val sha = MessageDigest.getInstance(hashFunction)
            var key: ByteArray = password.toByteArray(charset(charset))
            key = sha.digest(key)
            return SecretKeySpec(key, encryptionStandard)
        }

        //Format number
        fun formatNumber(str: String): String {
            if (str.trim { it <= ' ' }.isNotEmpty()) try {
                val value = str.toDouble()
                val formatter = DecimalFormat("#,###")
                return formatter.format(value).replace(',', '.')
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            return str
        }


    }


}