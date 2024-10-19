/**
 * 
 */
document.addEventListener("DOMContentLoaded", function() {
    const addButton = document.getElementById("add-details-btn");
    const movementDetails = document.getElementById("movement-details");

    if (addButton && movementDetails) {
        addButton.addEventListener("click", function(event) {
            event.preventDefault(); // 기본 동작 방지

            // 새로운 동선 추가하기
            const newRow = document.createElement("div");
            newRow.classList.add("movement-row");
            newRow.innerHTML = `
                <div class="movement-title">
                    <input type="text" name="plusTitle[]" class="movement-input-title" placeholder="제목을 입력하세요">
                </div>
                <div class="movement-content">
                    <input type="text" name="plusContent[]" class="movement-input-content" placeholder="내용을 입력하세요">
                </div>
            `;

            // 버튼 위에 새로운 div 추가
            movementDetails.insertBefore(newRow, addButton);
        });
    }
});


