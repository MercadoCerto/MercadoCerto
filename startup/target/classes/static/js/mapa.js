// ============================
// MAPA BASE
// ============================

const map = L.map('map').setView([-23.5505, -46.6333], 12);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
}).addTo(map);

let markers = [];

// ============================
// LIMPAR MARCADORES
// ============================

function limparMarcadores() {
    markers.forEach(m => map.removeLayer(m));
    markers = [];
}

// ============================
// ADICIONAR MARCADOR
// ============================

function adicionarMarcador(produto) {
    if (!produto.latitude || !produto.longitude) return;

    const imagemUrl = produto.imagem
        ? `http://localhost:8080/uploads/${encodeURIComponent(produto.imagem)}`
        : null;

    const popupHTML = `
        <b>${produto.nome}</b><br>
        Marca: ${produto.marca}<br>
        Categoria: ${produto.categoria}<br>
        Preço: R$ ${produto.preco.toFixed(2)}<br><br>

        ${imagemUrl 
            ? `<img src="${imagemUrl}" width="140" height="140"
                 style="object-fit:cover;border-radius:8px;border:1px solid #ccc;">`
            : "<i>Sem imagem</i>"}
    `;

    const marker = L.marker([produto.latitude, produto.longitude])
        .addTo(map)
        .bindPopup(popupHTML);

    markers.push(marker);
}

// ============================
// BUSCAR PRODUTOS NO BACKEND
// ============================

async function buscarProdutos(nome) {
    const resposta = await fetch("http://localhost:8080/api/produtos");
    const produtos = await resposta.json();

    return produtos.filter(p =>
        p.nome.toLowerCase().includes(nome.toLowerCase())
    );
}

// ============================
// AÇÃO DO BOTÃO BUSCAR
// ============================

document.getElementById("btnBuscar").addEventListener("click", async () => {

    const nome = document.getElementById("buscaProduto").value.trim();

    if (!nome) {
        alert("Digite o nome do produto!");
        return;
    }

    limparMarcadores();

    const encontrados = await buscarProdutos(nome);

    if (encontrados.length === 0) {
        alert("Nenhum produto encontrado.");
        return;
    }

    // Adiciona todos
    encontrados.forEach(adicionarMarcador);

    // Centraliza no primeiro resultado
    map.setView([encontrados[0].latitude, encontrados[0].longitude], 15);
});
