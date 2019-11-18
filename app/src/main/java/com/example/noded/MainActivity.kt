package com.example.noded

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import kotlinx.android.synthetic.main.activity_main.*
import com.example.noded.databinding.ContentMainBinding
import com.example.noded.databinding.ActivityMainBinding
import io.noties.markwon.Markwon
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.MarkwonEditorTextWatcher
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var bindingCont: ContentMainBinding
    private lateinit var bindingAct: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingCont = DataBindingUtil.setContentView(this, R.layout.content_main)
        bindingAct = DataBindingUtil.setContentView(this, R.layout.activity_main)



        setSupportActionBar(toolbar)

        bindingAct.fab.setOnClickListener { saveNote() }

        // Make the EditText update live
        val markwon: Markwon = Markwon.create(this)
        val editor: MarkwonEditor = MarkwonEditor.create(markwon)
        bindingCont.newNoteBody.addTextChangedListener(MarkwonEditorTextWatcher.withPreRender(
            editor,
            Executors.newCachedThreadPool(),
            bindingCont.newNoteBody))

    }

    private fun saveNote() {
        val filename = "noteFile"
        val fileContents = bindingCont.newNoteBody.text.toString()
        Toast.makeText(this,fileContents, Toast.LENGTH_LONG)
        this.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
