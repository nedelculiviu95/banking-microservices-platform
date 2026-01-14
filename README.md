# banking-microservices-platform

A small “banking” system with multiple services and strong focus on resilience and observability.

Services:

account-service: manage accounts (balance, status, limits).

transaction-service: handle transfers, top‑ups, withdrawals.

customer-service: store KYC data and link to accounts.

notification-service: send emails/SMS on successful or failed transactions.