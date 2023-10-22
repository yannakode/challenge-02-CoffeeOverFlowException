# CHALLENGE 02 - CoffeeOverflowException

O projeto consiste em uma API REST para um e-commerce, utilizando as tecnologias e conhecimentos aprendidos durante as trilhas.

## Funcionalidades

A API expõe endpoints para as seguintes funcionalidades:

1. **Pedido**: A funcionalidade Pedido permite que os usuários façam pedidos de produtos de um catálogo.
2. **Produto**: A funcionalidade Produto permite que os usuários criem, leiam, atualizem e excluam produtos.
3. **Feedback**: A funcionalidade Feedback permite que os usuários deixem feedbacks sobre produtos ou serviços.

### Pedido

A funcionalidade Pedido permite que os usuários façam pedidos de produtos de um catálogo. Um pedido é composto por uma lista de produtos, um endereço de entrega e uma forma de pagamento.

**Operações**:

| **Métodos** | **URL**            | **Descrição**                                                                                                                                                         |
|:------------|:-------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET         | /orders            | Lista pedidos: retorna a lista de pedidos ordenados por data de criação, dos mais recentes para os mais antigos. Além disso, é possível filtrar por status do pedido. |
| GET         | /orders/:id        | Buscar pedido: Retorna as informações de um pedido específico.                                                                                                        |
| POST        | /orders            | Cadastrar pedido: Cria um novo pedido.                                                                                                                                |
| PUT         | /orders/:id        | Atualizar pedido: atualiza as informações de um pedido existente como status ou data de entrega.                                                                      |
| POST        | /orders/:id/cancel | Cancelar pedido: Cancela um pedido existente.                                                                                                                         |

**Regras de negócio**:

* A operação GET `/orders` está ordenada por data de criação, dos pedidos mais recentes para os mais antigos. Além disso, é possível filtrar por status do pedido.
* Status dos pedidos: `CONFIRMED`, `SENT`, `CANCELED`
* Tipos permitidos de pagamento: `CREDIT_CARD`, `BANK_TRANSFER`, `CRYPTOCURRENCY`, `GIFT_CARD`, `PIX`, `OTHER`.
* O valor total do pedido é calculado pela aplicação de acordo com a seguinte fórmula: `total_value = subtotal_value - discount`
* O desconto de 5% é aplicado apenas para pedidos com método de pagamento `PIX`.
* Para o endereço, deve ser informado **apenas** `number`, `complement` e `postal_code`.
* Para complementar as informações relativas ao endereço, a API se comunicará com a API ViaCEP para buscar os demais dados.

**Regras de negócio para o cancelamento de pedidos:**

* Um pedido só pode ser cancelado se o status for diferente de `SENT`.
* Um pedido não pode ser cancelado se tiver mais de 90 dias de criação.
* Após o cancelamento, o status do pedido deverá ser alterado para `CANCELED`.

**Exemplo de payload**:

* **Request**

```json
{
  "products": [
    {
      "product_id": 1,
      "quantity": 2
    },
    {
      "product_id": 2,
      "quantity": 5
    }
  ],
  "address": {
    "street": "Street name",
    "number": 10,
    "postalCode": "31333333"
  },
  "payment_method": "PIX"
}
```

* **Response**

```json
{
  "id": 1,
  "products": [
    {
      "product_id": 1,
      "quantity": 2
    },
    {
      "product_id": 2,
      "quantity": 5
    }
  ],
  "address": {
    "street": "Street name",
    "number": 10,
    "complement": "details",
    "city": "City name",
    "state": "State name",
    "postal_code": "31333333"
  },
  "payment_method": "PIX",
  "subtotal_value": 100.00,
  "discount": 0.5,
  "total_value": 95.00,
  "created_date": "2023-07-20T12:00:00Z",
  "status": "CONFIRMED"
}
```

**Cancelar Pedido**:

* **Exemplo de Request**

```json
{
  "cancelReason": "Cancel reason"
}
```

* **Exemplo de Response**

```json
{
  "products": [
    {
      "product_id": 1,
      "quantity": 2
    },
    {
      "product_id": 2,
      "quantity": 5
    }
  ],
  "address": {
    "street": "Street name",
    "number": 10,
    "complement": "details",
    "city": "City name",
    "state": "State name",
    "postal_code": "31333333"
  },
  "payment_method": "PIX",
  "subtotal_value": 100.00,
  "discount": 0.5,
  "total_value": 95.00,
  "created_date": "2023-07-20T12:00:00Z",
  "status": "CANCELED",
  "cancel_reason": "Cancel reason",
  "cancel_date": "2023-07-20T12:00:00Z"
}
```

### Produto

A funcionalidade Produto permite que os usuários criem, leiam, atualizem e excluam produtos.

**Operações**:

| **Métodos** | **URL**       | **Descrição**                                                     |
|:------------|:--------------|:------------------------------------------------------------------|
| GET         | /products     | Lista produto: deve retornar todos os produtos.                   |
| GET         | /products/:id | Buscar produto: Retorna as informações de um produto específico.  |
| POST        | /products     | Cadastrar pedido: Cria um novo produto.                           |
| PUT         | /products/:id | Atualizar produto: atualiza as informações de um pedido existente |
| DELETE      | /products/:id | Excluir produto: Excluir um produto existente.                    |

**Regras de negócio**:

* Não será permitido a persistência de produtos com o mesmo nome.
* A descrição do produto deve ter no mínimo 10 caracteres.
* O valor do produto deve ser um número positivo.

**Exemplo de Payload**:

* **Exemplo de Request**

```json
{
  "name": "Product name",
  "description": "Product description",
  "value": 10.5
}
```

* **Exemplo de Response**

```json
{
  "id": 1,
  "name": "Product name",
  "description": "Product description",
  "value": 10.5
}
```

### Feedback

A funcionalidade Feedback permite que os usuários deixem feedbacks sobre produtos ou serviços. Um feedback é composto por uma escala de satisfação, um comentário e o ID do pedido relacionado.

**Operações**:

| **Métodos** | **URL**            | **Descrição**                                                           |
|:------------|:-------------------|:------------------------------------------------------------------------|
| GET         | /feedbacks         | Lista feedbacks: deve retornar todos os feedbacks.                      |
| GET         | /feedbacks/:id     | Buscar feedback por ID: Retorna as informações de um pedido específico. |
| POST        | /feedbacks         | Cadastrar feedback: Cria um novo feedback.                              |
| PUT         | /feedbacks/:id     | Atualizar feedback: atualiza as informações de um feedback existente    |
| DELETE      | /feedbacks/:id     | Excluir feedback: Excluir um feedback existente.                        |

**Regras de Negócios - Feedback**:

* Os níveis de satisfação permitidos são: `VERY_DISSATISFIED`, `DISSATISFIED`, `NEUTRAL`, `SATISFIED`, `VERY_ SATISFIED`.
* Não é permitido deixar feedbacks em pedidos com status `CANCELED`.

**Exemplo de Payload**:

* **Exemplo de Request**

```json
{
  "scale": "SATISFIED",
  "comment": "Comment here",
  "order_id": 1
}
```

* **Exemplo de Response**

```json
{
  "id": 1,
  "scale": "SATISFIED",
  "comment": "Comment here",
  "order_id": 1
}
```

## Fluxo de Erro

* Erro de Validação

```json
{
  "code": 400,
  "status": "Bad Request",
  "message": "O campo 'nome' é obrigatório.",
  "details": [
    {
      "field": "nome",
      "message": "O campo 'nome' é obrigatório."
    }
  ]
}
```

* Erro de Negócio

```json
{
  "code": 400,
  "status": "Bad Request",
  "message": "O pedido já foi cancelado.",
  "details": []
}
```

* Erro Inesperado

```json
{
  "code": 500,
  "status": "Internal Server Error",
  "message": "Ocorreu um erro inesperado.",
  "details": []
}
```

**Regras de Negócio para a resposta de exceção**:

* A resposta de exceção contém as seguintes informações:
    * `code`: Código de status HTTP
    * `status`: Status da resposta
    * `message`: Mensagem de erro
* O campo `details` é usado em alguns casos para fornecer informações adicionais sobre o erro.


