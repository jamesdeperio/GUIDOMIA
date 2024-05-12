import android.content.Context
import com.example.androiddemo.data.domain.car.MyObjectBox
import io.objectbox.BoxStore

object AppDatabase {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}
