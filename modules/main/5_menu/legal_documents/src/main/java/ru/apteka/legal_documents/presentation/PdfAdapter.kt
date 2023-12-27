package ru.apteka.licenses.presentation

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.graphics.pdf.PdfRenderer.Page
import android.os.ParcelFileDescriptor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.apteka.licenses.databinding.LicensesPdfHolderBinding
import java.io.File


/**
 * Представляет адаптер списка страниц pdf.
 * @param viewModel Модель отображения.
 */
class PdfAdapter(
    private val count: Int,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<PdfAdapter.ViewHolder>() {

    override fun getItemCount() = count

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LicensesPdfHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).also { vh ->
            vh.binding.flPdfPage.setOnClickListener {
                onItemClick(vh.adapterPosition)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    class ViewHolder(val binding: LicensesPdfHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            //val inputStream = binding.root.context.assets.open("sample.pdf")
            val f = File("${binding.root.context.cacheDir}/sample.pdf-pdfview.pdf")
            /*try {
                Log.d("myL", "f " + f.exists())
                FileOutputStream(f).use { output ->
                    val buffer = ByteArray(4 * 1024) // or other buffer size
                    var read: Int
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        output.write(buffer, 0, read)
                    }
                    output.flush()
                }
                Log.d("myL", "f " + f.exists())
            } catch (e: Exception) {
                Log.d("myL", "e " + e)
            }*/

            val pfd = ParcelFileDescriptor.open(f, ParcelFileDescriptor.MODE_READ_ONLY)
            val renderer = PdfRenderer(pfd)
            val page: Page = renderer.openPage(item)
            val b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            page.render(b, null, null, Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            renderer.close()

            binding.ivPdfPage.setImageBitmap(b)

            /*binding.pdfView.fromAsset("sample.pdf")
                .defaultPage(item)
                .load()*/
        }
    }
}