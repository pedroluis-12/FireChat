# FireChat 🔥💬

FireChat é um aplicativo de bate-papo para Android, desenvolvido para demonstrar as práticas modernas de desenvolvimento Android com Kotlin, Jetpack Compose e Firebase.

## 🚀 Funcionalidades

* **Visualização de Canais:** Exibe uma lista de canais de chat (contatos/grupos) obtidos em tempo real.
* **Criação de Canais:** Permite adicionar novos canais de chat que são instantaneamente sincronizados com o backend.
* **Navegação:** Navegação entre a tela principal e as telas de chat individuais.

## 🏗️ Arquitetura do Projeto

O projeto segue uma arquitetura limpa (Clean Architecture) com padrão MVVM (Model-View-ViewModel) na camada de apresentação. A estrutura é dividida em camadas para promover a separação de responsabilidades, testabilidade e manutenibilidade.

-   **`features`**: Contém as diferentes funcionalidades do aplicativo (ex: `login`, `register`, `home`).
    -   **`view`**: As telas da UI, construídas com Jetpack Compose.
    -   **`viewmodel`**: Responsável por gerenciar o estado da UI e expô-lo para a `view`. Ele interage com os `usecase`.
    -   **`usecase`**: Contém a lógica de negócio específica da funcionalidade. Ele orquestra a comunicação com um ou mais `repository`.
    -   **`repository`**: Abstrai a fonte de dados (local ou remota). É a única camada que sabe de onde os dados vêm (neste caso, o Firebase).
    -   **`model`**: Classes de dados que representam os objetos da funcionalidade.

## 🛠️ Tecnologias e Bibliotecas Utilizadas

-   **Linguagem**: [Kotlin](https://kotlinlang.org/)
-   **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) para uma UI declarativa e moderna.
-   **Injeção de Dependência**: [Hilt](https://dagger.dev/hilt/) para gerenciar as dependências do projeto.
-   **Programação Assíncrona**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) para lidar com operações em background.
-   **Carregamento de Imagens**: [Coil](https://coil-kt.github.io/coil/) para carregar e exibir imagens de forma eficiente.
-   **Navegação**: [Hilt Navigation Compose](https://developer.android.com/jetpack/compose/libraries#hilt-navigation) para integrar a navegação do Compose com o Hilt.
    
## ✍️ Configuração do Firebase

Para que o aplicativo funcione corretamente, é essencial configurar um projeto no **Firebase** e conectá-lo ao app.

---

### 1. Criar um Projeto Firebase

* Acesse o **Console do Firebase**.
* Clique em **"Adicionar projeto"** e siga as instruções.

---

### 2. Adicionar o App Android ao Projeto Firebase

* No painel do seu projeto, clique no ícone do **Android** para adicionar um novo aplicativo.
* Preencha o nome do pacote (**Package Name**). O do projeto atual é `com.pedroluis.projects.firechat`.
* Siga os passos e faça o download do arquivo `google-services.json`.

---

### 3. Adicionar o `google-services.json`

> **MUITO IMPORTANTE:** Coloque o arquivo `google-services.json` que você baixou na pasta `app/` do seu projeto Android.

---

### 4. Configurar o Realtime Database

* No menu lateral do console do Firebase, vá em **Build > Realtime Database**.
* Clique em **"Criar banco de dados"**.
* Inicie em **modo de teste**. Isso permite leitura e escrita sem autenticação, o que é ideal para o desenvolvimento inicial.
* **Atenção:** As regras de segurança padrão do modo de teste **expiram após 30 dias**. Para produção, você deve configurar **regras de segurança robustas**.

## ▶️Como Executar o Projeto

1.  **Clone este repositório:**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    ```
2.  **Abra o projeto** no Android Studio.
3.  Siga os passos da seção **Configuração do Firebase** para adicionar seu próprio arquivo `google-services.json` na pasta `app/`.
4.  Aguarde o **Gradle** sincronizar as dependências.
5.  **Execute o aplicativo** em um emulador ou dispositivo físico.

## 🗂️ Estrutura dos Dados no Firebase

O aplicativo utiliza o Firebase Realtime Database com uma estrutura simples para armazenar os canais.

### JSON

```json
{
  "channel": {
    "-OdsO4AZQkoh9ZO3nO0Y": "Nome do Canal 1",
    "-OdsOXq-pHeOUMRSFVIe": "Desenvolvimento Android",
    "-OutroIdUnicoGerado": "General"
  }
}
```
* `channel`: O nó raiz que agrupa todos os canais de chat.
* `-Ods...`: Uma **chave única (Push ID)** gerada automaticamente pelo Firebase (`database.getReference("channel").push().key`) ao criar um novo canal.
* `"Nome do Canal 1"`: O **valor** associado à chave, que neste caso é o nome do canal.
