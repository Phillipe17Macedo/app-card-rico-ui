package com.phillipe.mycard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardNumberEditText: EditText = findViewById(R.id.card_number)
        val cardExpiryEditText: EditText = findViewById(R.id.card_expiry)
        val cardHolderEditText: EditText = findViewById(R.id.card_holder)

        // Aplicando máscara para o número do cartão
        applyCardNumberMask(cardNumberEditText)

        // Aplicando máscara para a data de validade (MM/AA)
        applyCardExpiryMask(cardExpiryEditText)

        // Limite de caracteres para o nome do titular (até 30 caracteres)
        cardHolderEditText.filters = arrayOf(android.text.InputFilter.LengthFilter(30))
    }

    // Função para aplicar a máscara de número do cartão (**** **** **** ****)
    private fun applyCardNumberMask(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            private val mask = "#### #### #### ####"

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) {
                    isUpdating = false
                    return
                }

                isUpdating = true
                val unmasked = s.toString().replace(" ", "")
                val masked = StringBuilder()

                var i = 0
                for (m in mask) {
                    if (m != '#' && unmasked.length > i) {
                        masked.append(m)
                        continue
                    }
                    if (i >= unmasked.length) break
                    masked.append(unmasked[i])
                    i++
                }

                editText.setText(masked.toString())
                editText.setSelection(masked.length)
            }
        })
    }

    // Função para aplicar a máscara de data de validade (MM/AA)
    private fun applyCardExpiryMask(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) {
                    isUpdating = false
                    return
                }

                isUpdating = true
                val unmasked = s.toString().replace("/", "")
                val masked = StringBuilder()

                var i = 0
                for (ch in unmasked) {
                    if (i == 2) {
                        masked.append("/")
                    }
                    masked.append(ch)
                    i++
                }

                editText.setText(masked.toString())
                editText.setSelection(masked.length)
            }
        })
    }
}
