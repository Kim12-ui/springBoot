let map;
let center;
let savedMarkers = []; // 저장된 마커들의 배열
let markersData= [];

async function initMap() {
  const { Map } = await google.maps.importLibrary("maps");

  center = { lat: 37.5665, lng: 126.9780 };
  map = new Map(document.getElementById("map"), {
    center: center,
    zoom: 14,
    mapId: "4504f8b37365c3d0",
  });

  //마커의 InfoWindow(정보창)초기화
  infowindow = new google.maps.InfoWindow();
// 마커 배열 초기화
map.markers = []; // 이 부분을 추가하여 map.markers를 초기화합니다.

  
}

async function findPlaces(query) {
  const { Place } = await google.maps.importLibrary("places");
  //@ts-ignore
  const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
  const request = {
    textQuery: query,
    fields: ["displayName", "location", "rating","formattedAddress"],
    useStrictTypeFiltering: false,
  };
  //요청받은 정보를 places객체에 저장 
  const { places } = await Place.searchByText(request);

  //이전 검색 결과 마커 제거
  map.markers.forEach(marker => marker.setMap(null));
  map.markers = []; //기존 마커 배열 초기화


  //새로운 장소 검색 시 삭제 버튼 숨기기
  document.getElementById("deleteButton").style.display = "none";

  if (places.length) {

    const { LatLngBounds } = await google.maps.importLibrary("core");
    const bounds = new LatLngBounds();

    // places의 place 정보를 marker에 집어넣는 반복문 
    places.forEach((place) => {
      const markerView = new AdvancedMarkerElement({
        map,
        position: place.location,
        title: place.displayName,
      });
   //   console.log("주소: ", place.formattedAddress);
   //   console.log("장소이름: ", place.displayName);
      
      // 마커 클릭 시 InfoWindow열기
      markerView.addListener('click',()=>{
        infowindow.setContent(`
          <div>
            <h3>${place.displayName || "이름 없음"}</h3>
            <p>${place.rating ? `평점: ${place.rating}` : "평점 없음"}</p>
          </div>
        `);
        infowindow.setPosition(place.location);
        infowindow.open(map);

        currentMarker = markerView; //현재 마커 설정
      
        document.getElementById("locationName").innerText = place.displayName || "이름 없음";
        document.getElementById("locationRating").innerText = place.rating ? `평점: ${place.rating}` : "평점 없음";          
        document.getElementById("locationAddress").innerText = place.formattedAddress ? `${place.formattedAddress}` : "주소 정보 없음";          

        
        // "삭제" 버튼 보이기
        document.getElementById("deleteButton").style.display = "block";

         // "담기" 버튼 보이기 또는 숨기기
          if (savedMarkers.includes(currentMarker)) {
            document.getElementById("saveButton").style.display = "none"; // 이미 저장된 마커인 경우 담기 버튼 숨기기
          } else {
            document.getElementById("saveButton").style.display = "block"; // 새 마커인 경우 담기 버튼 보이기
          }
      });
      

      //마커를 저장하기 위해 배열에 추가
      map.markers.push(markerView);

      bounds.extend(place.location);
      
    });
    map.fitBounds(bounds);
    map.setCenter(bounds.getCenter());

    //지도가 너무 확대되지 않도록 최소 줌 레벨 설정
    const listener = google.maps.event.addListener(map, "bounds_changed", function() {
      if (map.getZoom() > 18) {  // 줌 레벨 제한
        map.setZoom(14);
      }
      google.maps.event.removeListener(listener);
    });
    const firstPlace = places[0]; // 첫 번째 장소의 정보를 가져옴

    // 장소 이름, 사진, 평점을 업데이트
    document.getElementById("locationName").innerText = firstPlace.displayName || "이름 없음";
    document.getElementById("locationRating").innerText = firstPlace.rating ? `평점: ${firstPlace.rating}` : "평점 없음";
    document.getElementById("locationAddress").innerText = firstPlace.formattedAddress ? `주소: ${firstPlace.formattedAddress}` : "주소 정보 없음";          

    // "담기" 버튼 보이기
     document.getElementById("saveButton").style.display = "block";
  } else {
    console.log("결과가 없습니다.");
  }

  //저장된 마커를 지도에 표시
displaySavedMarkers();
}

// 검색버튼 클릭시 실행되는 함수
  function handleSearch(){
    const query = document.getElementById("searchInput").value;
    if(query){
      findPlaces(query);
    }else{
      alert("장소 이름을 입력해주세요.")
    }
  }

// 담기 버튼 클릭시 실행되는 함수
  function saveMarkers(){
    const currentMarker = map.markers[map.markers.length - 1]; // 가장 최근에 추가된 마커
    if (currentMarker && !savedMarkers.includes(currentMarker)) {
        savedMarkers.push(currentMarker);
        currentMarker.setMap(map); // 지도에 계속 표시
        console.log("저장된 마커배열 : ", savedMarkers);
  
    // "담기" 버튼 안 보이게 하기
    document.getElementById("saveButton").style.display = "none";
    //"삭제"버튼 보이게 하기
    document.getElementById("deleteButton").style.display="block";
  }
}

//삭제 버튼 클릭 시 실행되는 함수
function removeMarkers(){
  if (currentMarker && savedMarkers.includes(currentMarker)) {
    currentMarker.setMap(null); // 지도에서 마커 제거
    
    // 저장된 마커 배열에서 제거
    // : savedMarkers배열에서 currentMarker와 일치하지 않는 마커들만 남긴다.
    // 특정 마커를 제거하는 코드
    // filter : 배열의 매서드 , 조건에 맞는 요소들만 모아서 새 배열을 만든다. 
    savedMarkers = savedMarkers.filter(marker => marker !== currentMarker); 
    // marker !== currentMarker 에서 true를 반환한 요소는 새 배열에 포함
    // false를 반환한 요소는 제외 
    console.log("삭제 후 남은 마커 배열: ", savedMarkers);

    // 정보 초기화 및 "삭제" 버튼 숨기기
    document.getElementById("locationName").innerText = "";
    document.getElementById("locationRating").innerText = "";
    document.getElementById("locationAddress").innerText = "";
    document.getElementById("deleteButton").style.display = "none";
    document.getElementById("saveButton").style.display = "block";
    document.getElementById("saveButton").style.display = "none"; // 담기 버튼도 숨기기
    infowindow.close(map);

    //현재 마커 초기화
    currentMarker = null;
  }else{
    console.log("삭제할 마커가 없습니다.")
  }
}

//저장된 마커를 지도에 표시하는 함수
function displaySavedMarkers(){
  savedMarkers.forEach(marker => {
    marker.setMap(map); 
  });
}

// 버튼 클릭 시 실행되는 함수
function handleSaveSchedule() {
  const markerInfoInput = document.getElementById("markerInfo");

  // 입력창 초기화
  markerInfoInput.value = ""; // 이전 내용을 지우기

  // markersData 배열 초기화
   markersData = []; // 빈 배열로 초기화

  savedMarkers.forEach(marker => {
   	const lat = marker.position.lat; // lat을 함수로 호출
    const lng = marker.position.lng; // lng을 함수로 호출
    const title = marker.title || "이름 없음"; // 마커의 제목(장소 이름) 가져오기
      

    // position이 올바른 객체인지 확인

    

      console.log(`${lat},${lng}, ${title}`);
      // 입력창 표시
      markerInfoInput.value += markersData;

      // 마커 정보 객체를 markersData 배열에 추가
      markersData.push({
        lat: lat, // 위도
        lng: lng, // 경도
        title: title // 장소 이름
      });

  });

  // markersData 배열을 확인하기 위해 콘솔에 출력
  console.log(markersData);
}

// saveSchedule 버튼에 이벤트 리스너 추가
document.getElementById("saveSchedule").addEventListener("click", handleSaveSchedule);

// 검색 버튼 클릭 이벤트 리스너
// document.getElementById("searchButton").addEventListener("click",handleSearch);

//엔터키 누를 때 이벤트 리스너
document.getElementById("searchInput").addEventListener("keypress",(event)=>{
  if(event.key === "Enter"){
    handleSearch();
  }
});



//담기 버튼 누를 때 이벤트 리스너
document.getElementById("saveButton").addEventListener("click",saveMarkers);

// 삭제 버튼 누를 때 이벤트 리스너
document.getElementById("deleteButton").addEventListener("click",removeMarkers);

initMap();

