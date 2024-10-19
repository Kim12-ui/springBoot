/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('scrollToPackage').addEventListener('click', function(event) {
        event.preventDefault();
        const target = document.querySelector('.pageName');
        if (target) {
            target.scrollIntoView({ behavior: 'smooth' });
        }
    });

    document.getElementById('scrollToTip').addEventListener('click', function(event) {
        event.preventDefault();
        const target = document.querySelector('.info-title');
        if (target) {
            target.scrollIntoView({ behavior: 'smooth' });
        }
    });
});