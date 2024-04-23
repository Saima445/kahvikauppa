// Haetaan yläpalkin elementit
let upperBar = document.querySelector(".navigation-items");
let navigationLinks = document.querySelectorAll(".navigation-items li a");
// Tallennetaan nykyinen scroll-arvo
let lastScrollTop = 0;
window.addEventListener("scroll", function () {
  // Sivun nykyinen scroll-arvo
  let scrollTop = window.pageYOffset || document.documentElement.scrollTop;
  // Tarkistetaan onko sivua rullattu ylös vai alaspäin
  if (scrollTop > lastScrollTop) {
    // Sivua rullattiin alaspäin, muutetaan yläpalkin väriä
    upperBar.style.backgroundColor = "whitesmoke";
    upperBar.style.border = "0.5px solid black";
    navigationLinks.forEach(function (link) {
      link.style.color = "black";
    });
  } else {
    // Sivua rullattiin ylöspäin, palautetaan väri
    upperBar.style.backgroundColor = "transparent";
    upperBar.style.border = "transparent";
    navigationLinks.forEach(function (link) {
      link.style.color = "whitesmoke";
    });
  }
  // Päivitetään sivun nykyinen scroll-arvo
  lastScrollTop = scrollTop <= 0 ? 0 : scrollTop;
});
