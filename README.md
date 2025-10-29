# Flipkart Offers API - Spring Boot Project

## 📋 Overview

This project provides RESTful APIs to:
1. **Ingest and store Flipkart offers** from their API.
2. **Identify the best applicable offer** based on bank and payment instrument.



## 🛠️ Setup Instructions

```bash
git clone https://github.com/piyush2cool/PiePayOffer.git
cd PiePayOffer
mvn spring-boot:run
```

Or with IntelliJ/Eclipse, just import the Maven project and run `FlipkartOffersApplication`.


---

## 🚀 Endpoints

### 1. `POST /api/offer`

Stores new offers.

**Request Body**:
```
{
  "flipkartOfferApiResponse": {
    "offers": [
      {
        "title": "10% off with Axis Bank",
        "bankName": "AXIS",
        "type": "CREDIT",
        "minTxnAmount": 5000,
        "maxDiscountAmount": 500,
        "discountPercent": 10,
        "paymentOptions": ["CREDIT"]
      },
      {
        "title": "5% Cashback with IDFC EMI",
        "bankName": "IDFC",
        "type": "EMI_OPTIONS",
        "minTxnAmount": 7000,
        "maxDiscountAmount": 400,
        "discountPercent": 5,
        "paymentOptions": ["EMI_OPTIONS"]
      }
    ]
  }
}
```

**Response**:
```
{
  "noOfOffersIdentified": 2,
  "noOfNewOffersCreated": 2
}
```

---

### 2. `GET /api/highest-discount`

Returns highest applicable discount.

**Query Params**:
- `amountToPay`: `double`
- `bankName`: `string`
- `paymentInstrument`: `string` (`CREDIT`, `EMI_OPTIONS`)

**Request**:
```
GET /api/highest-discount?amountToPay=10000&bankName=AXIS&paymentInstrument=CREDIT
```

**Response**:
```
{
  "highestDiscountAmount": 500
}
```

---
## 📂 Assumptions

- The Flipkart offer response structure is consistent and includes the fields used above.
- Offers are unique by `title + bankName`.
- Only `CREDIT` and `EMI_OPTIONS` are valid payment instruments.

---

## ⚙️ Design Choices

- **Spring Boot**: For rapid development and robust ecosystem.
- **H2 Database**: In-memory DB for demo/testing ease.
- **Lombok**: To reduce boilerplate.
- **JPA**: For database interactions.

---
