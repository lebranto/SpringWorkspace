//iife 즉시 실행 함수

(
    function (){
        const display = document.querySelector(".display-chatting");
        display.scrollTop = display.scrollHeight;
    }


)();



// 1. 채팅 메세지 보내기 기능
document.getElementById("send").addEventListener("click",sendMessage);

function sendMessage(){
    var input = document.getElementById("inputChatting");

    if(input.value.trim().length ==0){
        alert("한 글자 이상 입력하세요")
        input.value=""
        input.focus();
        reuturn
    }

    var chatMessage = {
        message : input.value ,
        chatRoomNo,
        userNo,
        userName
    };

    var json = JSON.stringify(chatMessage); // 문자열로 치환하기 위한 문장
    chattingSocket.send(json);

    input.value="";

}

// 서버에서 전달하는 메세지를 처리하는 이벤트 핸들러
chattingSocket.onmessage = function(e){
     // 서버에서 전달한 json 데이터를 js로 파싱
     const chatMessage = JSON.parse(e.data); // 자바 스크립트 객체로 바꿔주는 코드


     const li = document.createElement("li");
     const p = document.createElement("p");

     p.classList.add("chat");
     p.innerHTML = chatMessage.message;
     const span = document.createElement("sapn");
     span.classList.add("chatDate");
     span.innerHTML = chatMessage.createDate;


     if(chatMessage.userNo == userNo){
        li.classList.add("myChat");
        li.append(span, p);
     } else{
        li.innerHTML =`<b>${chatMessage.userName}<b>`
        li.append(p,span);
     }

     const display = document.querySelector(".display-chatting");
     display.append(li); 
     
     display.scrollTop = display.scrollHeight // 스크롤을 자동으로 내려가게 끔 만드는 코드

}
