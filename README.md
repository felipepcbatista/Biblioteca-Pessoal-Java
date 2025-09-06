# Biblioteca Pessoal (Projeto em Java)

## 1. Objetivo
Sistema de linha de comando (CLI) para gerenciar uma pequena biblioteca pessoal, com usuários, livros e empréstimos. Projeto desenvolvido em Java para treinar *POO, camadas de software e teste automatizado.*

---

## 2. Entidades do domínio

### 2.1 Livro
* id (long)
* titulo (String)
* autor (String)
* ano (int)
* status -> _DISPONÍVEL | EMPRESTADO_
* Regras
** Um livro só pode estar em um status de cada vez.
** Se EMPRESTADO, existe 1 empréstimo aberto vinculado.

### 2.2 Usuário
* id (long)
* nome (String)
* email (String, formato simples _x@y_)
* Regras
** O e-mail deve ser válido.
** Usuário não pode pegar novo livro se tiver empréstimo em atraso aberto.

### 2.3 Empréstimo
* id (long)
* livroId (long)
* usuarioId (long)
* dataEmprestimo (LocalDate)
* dataPrevista (LocalDate = dataEmprestimo + 7)
* dataDevolucao (LocalDate | null)

* Regras
** Enquanto _dataDevolucao == null_, empréstimo está ABERTO.
** Se _dataDevolucao != null_, está DEVOLVIDO.

---

## 3. Regras de negócio
* *Prazo padrão*: 7 dias para devolução.
* *Multa*: R$ 1,00 por dia de atraso.
* *Bloqueio por atraso*: se o usuário tem atraso aberto, não pode pegar novo livro.
* *Livro único*: não pode ter dois empréstimos abertos para o mesmo livro.

---

## 4. Casos de uso

### 4.1 Emprestar livro

* Pré-condições:
** Livro existe e está DISPONIVEL.
** Usuário existe e não tem atraso aberto.

* Fluxo:
** Calcular data prevista (+7 dias).
** Criar empréstimo.
** Marcar livro como EMPRESTADO.

* Pós-condições:
** Empréstimo salvo como ABERTO.
** Livro indisponível.

### 4.2 Devolver livro

* Pré-condições:
** Empréstimo existe e está ABERTO.

* Fluxo:
** Registrar data de devolução.
** Calcular multa (se hoje > dataPrevista).
** Marcar livro como DISPONIVEL.

* Pós-condições:
** Empréstimo salvo como DEVOLVIDO.
** Multa registrada.

### 4.3 Relatórios

* Listar livros disponíveis.
* Listar empréstimos em atraso.
