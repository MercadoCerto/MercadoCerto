// Converte média em estrelas
function converterEstrelas(media) {
    const total = Math.round(media);
    return "★★★★★☆☆☆☆☆".slice(5 - total, 10 - total);
}

// Buscar média de avaliação
async function buscarMedia(idMercado) {
    const res = await fetch(`http://localhost:8080/api/avaliacoes/media/${idMercado}`);
    return await res.json();
}

// Carregar ranking
async function carregarRanking() {
    const res = await fetch("http://localhost:8080/api/mercados");
    const mercados = await res.json();

    const lista = [];

    // Busca média de cada mercado
    for (const m of mercados) {
        const media = await buscarMedia(m.id);
        lista.push({
            ...m,
            media: media
        });
    }

    // Ordena por média (maior → menor)
    lista.sort((a, b) => b.media - a.media);

    const container = document.getElementById("rankingLista");
    container.innerHTML = "";

    lista.forEach((mercado, index) => {
        const pos = index + 1;

        const card = document.createElement("div");
        card.className = "produto-card";

        card.innerHTML = `
            <div class="produto-info">

                <h3>
                    ${pos}º — ${mercado.nome}
                </h3>

                <p><strong>Endereço:</strong> ${mercado.endereco}</p>

                <p style="margin-top:5px;">
                    <strong>Média:</strong>
                    <span style="color:gold;">${converterEstrelas(mercado.media)}</span>
                    (${mercado.media.toFixed(1)})
                </p>

            </div>
        `;

        container.appendChild(card);
    });
}

carregarRanking();
