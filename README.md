# API REST distancia-facil - versão 1.0.0
<p align="center">API para calcular a distancia entre os enderecos informados</p>
<br />
<p>A API pode ser acessada de duas formas:<br/><br/>
1) Através de endereços digitados manualmente:<br/>
URL: localhost:8080/distancias<br/>
JSON de exemplo:<br/>
[
    "Av. Rio Branco, 1 Centro, Rio de Janeiro RJ, 20090003", 
    "Praça Mal. Âncora, 122 Centro, Rio de Janeiro RJ, 20021200", 
    "Rua Lauro Muller 116, Botafogo, Rio de janeiro,RJ, 22290160",
    "Rua Jose Luiz Flaquer, 687, Éden, Sorocaba, SP",
    "Av. Independência, 1500, Éden, Sorocaba, SP"
]<br/><br/> 
1) Através de endereços estruturados:<br/>
URL: localhost:8080/distancias/enderecos<br/>
JSON de exemplo:<br/>
[
    {
        "logradouro": "Av. Rio Branco",
        "numero": "1",
        "bairro": "Centro",
        "cidade": "Rio de Janeiro",
        "uf": "RJ",
        "pais": "Brasil",
        "cep": "20090003"
    },
    {
        "logradouro": "Praça Mal. Âncora",
        "numero": "122",
        "bairro": "Centro",
        "cidade": "Rio de Janeiro",
        "uf": "RJ",
        "pais": "Brasil",
        "cep": "20021200"
    },
    {
        "logradouro": "Rua da Penha",
        "numero": "200",
        "bairro": "Centro",
        "cidade": "Sorocaba",
        "uf": "SP",
        "pais": "Brasil"
    }
]
<br/><br/>
Possível melhoria em versões futuras:<br/>
* Implementar opção de exibir a distância em outras unidades de medida (Atualmente apenas utiliza KM).
