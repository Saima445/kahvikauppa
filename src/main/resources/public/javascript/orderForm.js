// Alusta tyhjä tilauslista
let orderItems = {};
let timerId;

// Lisää uusi piilotettu input-kenttä formin sisälle
function addCartItemToForm(productId, productName, productPrice) {
  let orderForm = document.getElementById("orderForm");
  let orderInput = document.createElement("input");
  orderInput.type = "text";
  orderInput.style.display = "none";
  orderInput.name = "order";
  orderInput.value =
    "ID:" +
    productId +
    ", NIMI: " +
    productName +
    ", HINTA: " +
    productPrice +
    "€";
  orderForm.appendChild(orderInput);
}

// Tilauslistaan lisääminen
window.addToCart = function (productInfo) {
  let productData = productInfo.split(":");
  let productId = productData[0];
  let productName = productData[1];
  let productPrice = parseFloat(productData[2]);

  if (productName in orderItems) {
    // Tuote on jo tilauslistalla, päivitä sen määrää
    orderItems[productName].count++;
    updateCartItem(productName);
    addCartItemToForm(productId, productName, productPrice);
  } else {
    // Lisää tuote tilauslistalle ensimmäistä kertaa
    orderItems[productName] = {
      count: 1,
      price: productPrice,
    };

    // Lisää tuote listalle näytölle
    addCartItem(productName, productPrice);
    // Lisää uusi piilotettu input-kenttä formin sisälle
    addCartItemToForm(productId, productName, productPrice);
    showOrderList();
    // Käynnistä ajastin vain jos se ei ole vielä käynnissä
    if (!timerId) {
      timerId = setTimeout(hideOrderList, 3000); // Piilota suosikkilista 4 sekunnin kuluttua
    }
  }

  document.getElementById("orderFormContainer").style.display = "block"; // Näytä tilauslistan container
  // Siirrä tilauslista oikeaan reunaan
  document.getElementById("orderFormContainer").classList.add("order-list");
  document.getElementById("order-button").classList.add("input.submit-button");
};
// Lisää tuote listalle näytölle
function addCartItem(productName, productPrice) {
  let orderList = document.getElementById("orderList");
  let listItem = document.createElement("li");
  listItem.id = productName;
  listItem.textContent = `1 kpl, tuote: ${productName}, hinta: ${productPrice.toFixed(
    2
  )}€`;
  orderList.appendChild(listItem);
  updateTotal();
}
// Päivitä tilauslistan tuotteen määrä
function updateCartItem(productName) {
  let listItems = document
    .getElementById("orderList")
    .getElementsByTagName("li");
  for (let item of listItems) {
    if (item.textContent.includes(productName)) {
      let count = parseInt(item.textContent.split(" ")[0]);
      item.textContent = `${count + 1} kpl, tuote: ${productName}, hinta: ${(
        orderItems[productName].price *
        (count + 1)
      ).toFixed(2)}€`;
      break;
    }
  }
  updateTotal();
}
// Päivitä total tilauslistalle
function updateTotal() {
  let total = 0;
  // Laske total summa tilauslistalla olevista tuotteista
  for (let productName in orderItems) {
    total += orderItems[productName].count * orderItems[productName].price;
  }
  // Etsi total-elementti ja päivitä total-summa näytölle
  let totalElement = document.getElementById("total");
  totalElement.textContent = `Yhteensä: ${total.toFixed(2)}€`;
}

// Piilota tilauslista
window.hideOrderList = function () {
  document.getElementById("orderFormContainer").style.display = "none";
  // näytä sydän ikoni
  document.getElementById("basket-icon").style.display = "block";
  // Nollaa ajastin
  timerId = null;
};
// Näytä tilauslista
window.showOrderList = function () {
  // Nollaa aiempi ajastin ja käynnistä uusi
  clearTimeout(timerId);
  document.getElementById("orderFormContainer").style.display = "block";
  // Piilota sydänikoni
  document.getElementById("basket-icon").style.display = "none";
};
