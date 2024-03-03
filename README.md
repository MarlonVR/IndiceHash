# Implementação de Índice Hash Estático

Projeto 1 da disciplina "Projeto Banco de Dados" visando a implementação de um índice hash estático com interface gráfica, ilustrando as estruturas de dados e o funcionamento interno do índice. O índice é construído a partir de um conjunto de dados consistindo em 466 mil palavras em inglês, com cada palavra funcionando como uma chave única.

## Características

- **Interface Gráfica:** Ilustração visual das estruturas de dados e do processo de indexação e busca.
- **Funcionalidades Principais:**
    - Construção do índice hash estático.
    - Busca por tuplas usando uma chave de busca.
    - Table scan das primeiras X tuplas.

## Entidades/Estruturas Implementadas

- **Tupla:** Representa uma linha da tabela, contendo a chave de busca e os dados da linha.
- **Tabela:** Contém todas as tuplas construídas a partir dos dados carregados.
- **Página:** Estrutura de dados para divisão e alocação física da tabela na mídia de armazenamento.
- **Bucket:** Mapeia chaves de busca em endereços de páginas.
- **Função Hash:** Projetada para mapear uma chave de busca em um endereço de bucket.

## Configuração

### Parâmetros

- **Arquivo de Dados:** [Link para o arquivo de dados](https://github.com/dwyl/english-words)
- **Tamanho da Página:** Definido pelo usuário.
- **Quantidade de Páginas:** Calculado com base no tamanho da página e no total de dados.
- **Número de Buckets (NB):** Calculado para otimizar a indexação e busca.
- **Tamanho dos Buckets (FR):** Depende da função hash e define o número máximo de tuplas por bucket.

### Execução

1. Carregar o arquivo de dados em memória.
2. Gerar tuplas para cada linha do arquivo e adicionar à tabela.
3. Dividir as tuplas em páginas conforme o tamanho definido.
4. Criar buckets e aplicar a função hash para mapear tuplas aos buckets.

## Problemas e Soluções

- **Colisões:** Implementação de um algoritmo de resolução de colisões.
- **Overflow dos Buckets:** Algoritmo de resolução de overflow para garantir a eficiência da estrutura.

## Estatísticas

- Taxa de colisões.
- Taxa de overflows.
- Estimativa de custo (acessos a disco) para buscas.

## Critérios de Avaliação

- Interface gráfica, carga de dados, funcionamento das buscas, cálculos de parâmetros, taxas de colisões e overflows, e table scan.

