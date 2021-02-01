package com.example.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import com.example.notification.Notification.notification
import kotlinx.android.synthetic.main.activity_main.*
// 다른 패키지에서 코틀린 파일 import 하기
import com.example.notification.Notification.notification.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {
            // 8. notification 함수 호출
            // 채널 id와 이름 지정
            val builder1 = getNotification("chanel1", "첫 번째 채널")

            // 9. notification의 작은 아이콘 설정(상단 작업표시줄에 상시 표시되는 작은 아이콘)
            builder1.setSmallIcon(android.R.drawable.ic_menu_search)

            // 10. notification의 큰 아이콘 설정(작업표시줄을 늘려서 볼 때 나오는 Notification 아이콘)
            // 큰 아이콘은 bitmap으로만 받아서 표시할 수 있다
            // 그래서 bitmap으로 디코딩을 해야한다
            val bitmap  = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            builder1.setLargeIcon(bitmap)

            // 숫자 설정(알림 메시지 안에 숫자 표시가능)
            // 주로 미확인 문자 메시지 수를 표기할 때 사용
            builder1.setNumber(100)

            // 알람이 계속 뜬 상채로 있게하기
            builder1.setOngoing(true)

            // 전체 알락 삭제를 눌러도 삭제 안되게 하기
            builder1.setAutoCancel(true)

            // 11. notification 타이틀 설정
            builder1.setContentTitle("타이틀1")

            // 12. notification 메시지 설정
            builder1.setContentText("텍스트1")

            // 13. getNotification.build(): 주어진 정보(옵션)를 종합하여 새로운 Notification 객체 반환
            val notification = builder1.build()

            // 14. notification 을 제어할 수 있는 getSystemService 객체 생성
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // 15. notification 생성
            // id: 채널 id를 의미함
            manager.notify(10, notification)
        }
        button2.setOnClickListener {
            val builder1 = getNotification("chanel2", "두 번째 채널")

            // 작은 아이콘 설정(상단 작업표시줄에 상시 표시되는 작은 아이콘)
            builder1.setSmallIcon(android.R.drawable.ic_menu_search)

            // 큰 아이콘 설정(작업표시줄을 늘려서 볼 때 나오는 Notification 아이콘)
            // 큰 아이콘은 bitmap으로만 받아서 표시할 수 있다
            // 그래서 bitmap으로 디코딩을 해야한다
            val bitmap  = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            builder1.setLargeIcon(bitmap)

            // 숫자 설정(알림 메시지 안에 숫자 표시가능)
            // 주로 미확인 문자 메시지 수를 표기할 때 사용
            builder1.setNumber(100)

            // 타이틀 설정
            builder1.setContentTitle("타이틀2")

            // 메시지 설정
            builder1.setContentText("텍스트2")

            //Notification 생성
            val notification = builder1.build()

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // 17. 이 부분에서 id를 재설정 가능
            manager.notify(20, notification)
        }
        button3.setOnClickListener {
            // 다른 패키지의 코틀린 파일의 생성자 만들기
            val makeNotification : notification = notification()
            val builder1 = makeNotification.makeNotification(this, "chanel3",
                    "세 번째 알람", getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

            builder1.setSmallIcon(android.R.drawable.ic_menu_search)
            builder1.setContentTitle("테스트3")
            builder1.setContentText("테스트 내용3")

            val notification = builder1.build()

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.notify(30, notification)
        }
    }
    // 안드로이드 버전별로 Notification 실행 방법 달리하기
    // 1. notification 를 생성할 함수를 정의한다.
    // id: 각 채널의 이름(내부적 = 사용자에게 보이지 않음)
    // name: 사용자에게 보여줄 채널의 이름
    fun getNotification(id:String, name:String) : NotificationCompat.Builder{
        // OS 버전 분기
        // 안드로이드 8.0 이상이라면
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // 2. 알림 메시지를 관리하는 객체 생성
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // 3. 채널 객체 생성
            // IMPORTANCE_HIGH로 하지 않으면 메시지가 안나올 가능성 있음
            val chanel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)

            // 4. 단말기에 LED 가 있을 경우 LED도 함께 출력
            // LED 가 없는 단말기의 경우 그냥 자동으로 무시됨
            chanel.enableLights(true)
            // LED 색상 설정
            chanel.lightColor = Color.RED

            // 5. Notification 보여줄 때 진동 사용 여부
            chanel.enableVibration(true)

            // 6. 알림 메시지를 관리하는 객체에 채널을 등록한다
            manager.createNotificationChannel(chanel)

            // 7. 알림 컨텐츠를 생성한다
            val builder = NotificationCompat.Builder(this, id)
            return builder
        }else{
            // Builder에 deprecate가 생기는 이유는 8.0 이상부턴 지원하지 않기 때문
            val builder = NotificationCompat.Builder(this)
            return builder
        }
    }
}