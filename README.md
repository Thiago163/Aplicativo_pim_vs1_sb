# Documentação Técnica - Sistema Mobile para Gestão de Fazenda Urbana

## UNIVERSIDADE PAULISTA

### Equipe de Desenvolvimento:
- **ARTHUR NAKATA GOMES** - RA: N099593
- **GUSTAVO RODRIGUES COSTA** - RA: N095334 
- **JOÃO PEDRO PINHEIRO RAMOS SOARES** - RA: N0873H1 
- **PABLO DA SILVA RUFINO** - RA: R0133H9 
- **TIAGO FERREIRA DOS SANTOS** - RA: N046BH2
- **THIAGO SILVA DA CRUZ (líder em geral e de desenvolvimento)** - RA: F34JIJ0 

---

## 1. Introdução

Esta documentação técnica descreve o funcionamento do sistema mobile para gestão de uma fazenda urbana, desenvolvido para permitir ao usuário interagir com produtos e campos, além de possibilitar compras e o gerenciamento de perfis. O sistema está disponível em plataformas móveis e oferece uma interface para facilitar o controle da produção agrícola urbana, incluindo o gerenciamento de ciclo de plantação, colheita, adubagem e controle de água.

---

## 2. Funcionalidades Obrigatórias

### 2.1 Cadastro e Login
- **Objetivo**: Permitir que os usuários se cadastrem e façam login no aplicativo.
- **Descrição**: O usuário pode se registrar clicando no botão “Realizar Cadastro” e fornecendo informações pessoais para criar uma conta. Para os usuários já registrados, a opção “Entrar” permite o acesso à conta por meio de e-mail e senha.

### 2.2 Gerenciamento de Perfil
- **Objetivo**: Permitir ao usuário visualizar, editar e excluir suas informações pessoais.
- **Descrição**: O usuário pode acessar a tela de edição de perfil para modificar dados como nome, endereço e e-mail.

### 2.3 Listagem de Produtos
- **Objetivo**: Exibir os produtos disponíveis na plataforma.
- **Descrição**: O sistema lista os produtos cadastrados com detalhes como quantidade disponível, preço e outras informações relevantes.

### 2.4 Visualização de Produtos
- **Objetivo**: Exibir uma lista de produtos com informações básicas.
- **Descrição**: O usuário pode visualizar os produtos listados, com nome, preço e imagem de cada item.

### 2.5 Adicionar à Cesta
- **Objetivo**: Permitir que o usuário selecione e adicione produtos à cesta de compras.
- **Descrição**: Na tela de vendas, o usuário pode adicionar produtos ao carrinho, verificando a quantidade disponível e o preço.

### 2.6 Gerenciamento da Cesta
- **Objetivo**: Gerenciar os produtos no carrinho de compras.
- **Descrição**: O usuário pode alterar as quantidades de itens ou excluir produtos da cesta.

### 2.7 Simulação de Fechamento de Pedido
- **Objetivo**: Permitir ao usuário revisar itens no carrinho e calcular o total da compra antes de finalizar o pedido.
- **Descrição**: Após revisar os itens no carrinho, o usuário pode clicar em “Finalizar Compra” para concluir o processo de compra.

---

## 3. Estrutura e Funcionalidades do Sistema

### 3.1 Tela Inicial
- **Objetivo**: Apresentar as opções de acesso ao sistema.
- **Descrição**: Na tela inicial, o usuário verá um botão de “Cadastro” que o direcionará para a tela de registro, caso ainda não esteja autenticado.

### 3.2 Navegação Após o Login
- **Tela Inicial Após Login**: Após o login, o sistema exibe botões principais para a gestão da fazenda, com seções de controle para gerenciamento do ciclo, água, colheita e adubagem.
- **Campo "Ciclo"**: O primeiro botão permite o cadastro de novos campos de plantação.
- **Controles da Fazenda**: Abaixo de “Ciclo”, o usuário encontra três seções principais: Controle de Água, Controle de Colheita e Controle de Adubagem.

### 3.3 Gestão de Produtos e Carrinho
- **Menu Principal**: O menu contém quatro botões principais para navegação:
  - **Vendas**: Acesso à visualização e adição de produtos ao carrinho.
  - **Campos**: Exibição dos campos cadastrados.
  - **Fechamento**: Permite revisar os produtos e campos antes de concluir uma ação.
  - **Carrinho**: Exibe os itens adicionados ao carrinho.

### 3.4 Classe Carrinho
- **Objetivo**: Gerenciar a seleção e manipulação dos produtos no carrinho de compras.
- **Funcionalidade**: O usuário pode adicionar, remover ou modificar produtos, além de calcular o valor total da compra.

### 3.5 Adicionando Produtos ao Carrinho
- **Objetivo**: Permitir ao usuário adicionar e modificar produtos no carrinho.
- **Descrição**: O usuário pode selecionar produtos na tela de vendas e, posteriormente, alterar a quantidade ou remover produtos do carrinho.

---

## 4. Estrutura de Campos e Produtos

### 4.1 Campos
- **Objetivo**: Gerenciar as áreas de cultivo e os ciclos das plantações.
- **Classe Campo**: Armazena dados sobre o campo, como nome, descrição, status e data de colheita.
- **AdapterCampo**: Responsável por vincular as informações dos campos à interface de usuário.

### 4.2 Produtos
- **Objetivo**: Gerenciar os itens cultivados ou estocados.
- **Classe Produto**: Armazena dados como nome, foto, descrição, preço e quantidade disponível.
- **AdapterProduto**: Gerencia a lista de produtos e as interações com a interface de usuário.

---

## 5. Tecnologias e Ferramentas Utilizadas

- **Mobile**: Java (para a versão mobile)
- **Web**: .NET MVC Razor (para a versão web)
- **Desktop**: C# (para a versão desktop)

---

## 6. Conclusão

Este sistema foi desenvolvido com o objetivo de otimizar a gestão de fazendas urbanas, facilitando a interação do usuário com produtos agrícolas e o processo de compras. Com funcionalidades como cadastro de produtos, controle de campos de cultivo e gerenciamento de cesta de compras, o sistema oferece uma solução prática para os gestores de fazendas urbanas.
