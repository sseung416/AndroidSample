package com.example.mygallary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide

//(1)
//컴파일 시간에 결정되는 상수, 파일 내 어디서든 사용가능
private const val ARG_URI = "uri"

class PhotoFragment : Fragment() {
    private var uri: String? = null
    private lateinit var imageView: ImageView

    //(3)
    //프래그먼트 생성 -> onCreate() 메서드 호출 -> ARG_URI 키에 저장된 uri값을 얻어 변수에 저장
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uri = it.getString(ARG_URI) //ARG_URI 키에 저장된 uri값 얻어 변수에 저장
        }
    }

    //(4)
    //onCreateView(): 프래그먼트에 표시될 뷰 생생
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //inflate(): 액티비티가 아닌 곳에서 레이아웃 리소스를 가져옴
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    //뷰가 생성된 직후 호출
    //view: 생성된 뷰, savedInstanceState: 상태를 저장하는 객체
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.imageView)
        //Glide.with(this): 사용 준비
        //load(): 인자로 전달 받은 이미지 로딩
        //into(): 인자로 전달된 뷰에 표시
        Glide.with(this).load(uri).into(imageView)
    }

    companion object {
        @JvmStatic
        //(2)
        //newInstance() 메서드를 이용해 프레그먼트 생성, 인자로 uri값 전달
        //전달된 값은 Bundle 객체에 ARG_URI 키로 저장되고 arguments 프로퍼티에 저장됨
        fun newInstance(uri: String) =
                PhotoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_URI, uri)
                    }
                }
    }
}