# Chatbot de Atendimento Orientado a Eventos

Chatbot de atendimento em arquitetura de microsserviços, com comunicação assíncrona
via Kafka. O usuário pergunta pelo pedido em linguagem natural; o sistema interpreta
a intenção, consulta a base e responde.

## Arquitetura

```
   POST /chat
       │
       ▼
┌──────────────────┐   chat.messages   ┌──────────────────┐
│   chat-gateway   │ ────────────────▶ │  intent-service  │
│                  │                   │                  │
│  REST + histórico│ ◀──────────────── │  intenção + regra│
└──────────────────┘   chat.replies    └──────────────────┘
       │                                        │
       ▼                                        ▼
   MongoDB                                  PostgreSQL
  (conversa)                                 (pedidos)
```