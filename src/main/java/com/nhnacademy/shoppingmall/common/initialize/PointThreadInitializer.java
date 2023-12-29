package com.nhnacademy.shoppingmall.common.initialize;

import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.worker.WorkerThread;
import java.util.Set;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointThreadInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {

        RequestChannel requestChannel = new RequestChannel(10);
        //todo#14-1 servletContext에 requestChannel을 등록합니다.
        ctx.setAttribute("requestChannel", requestChannel);

        //todo#14-2 WorkerThread 사작합니다.
        log.debug("PointThreadInitializer onStartup");
        WorkerThread workerThread = new WorkerThread(requestChannel);
        workerThread.start();

        /**
         * 생성자 - 소비자
         * Channel : A와 B가 데이터를 주고 받는 통로
         *
         * DbConnectionThreadLocal.initialize()를 통해서
         * 별도 connection을 생성?
         *
         * 주문이 완료가 되어야 포인트 적립
         *
         * 주문 : 생성자
         * 포인트 적립 : 소비자
         *
         * 비동기 프로그램,,
         *
         * Queue를 어떻게 사용할 것인지가 중요
         */

    }
}
