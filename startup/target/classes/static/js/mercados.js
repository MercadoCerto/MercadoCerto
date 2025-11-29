let mercadoSelecionado = 0;
let notaSelecionada = 0;

document.addEventListener("DOMContentLoaded", () => {
    configurarEstrelas();
    configurarBotaoEnviar();
    carregarMercados();
});

// ==========================================
// CARREGAR MERCADOS
// ==========================================
async function carregarMercados() {
    const res = await fetch("http://localhost:8080/api/mercados");
    const mercados = await res.json();

    const container = document.getElementById("listaMercados");
    container.innerHTML = "";

    for (const mercado of mercados) {

        const media = await buscarMedia(mercado.id);

        const card = document.createElement("div");
        card.className = "produto-card";

        card.innerHTML = `
            <div class="produto-info">
                <h3>${mercado.nomeMercado}</h3>

                <p><strong>Latitude:</strong> ${mercado.latitude || "Sem GPS"}</p>
                <p><strong>Longitude:</strong> ${mercado.longitude || "Sem GPS"}</p>

                <p style="margin-top:5px;">
                    <strong>Média:</strong>
                    <span style="color:gold;">${converterEstrelas(media)}</span>
                    (${media.toFixed(1)})
                </p>

                <button class="botao-cadastro" 
                        style="margin-top:12px; width:180px;"
                        onclick="abrirModal(${mercado.id})">
                    Avaliar
                </button>
            </div>
        `;

        container.appendChild(card);
    }
}

// ==========================================
// BUSCAR MÉDIA
// ==========================================
async function buscarMedia(idMercado) {
    const res = await fetch(`http://localhost:8080/api/avaliacoes/media/${idMercado}`);
    return await res.json();
}

// ==========================================
// ESTRELAS VISUAIS PARA A MÉDIA
// ==========================================
function converterEstrelas(media) {
    const rounded = Math.round(media);

    let estrelas = "";

    for (let i = 1; i <= 5; i++) {
        estrelas += i <= rounded ? "★" : "☆";
    }

    return estrelas;
}

// ==========================================
// ABRIR / FECHAR MODAL
// ==========================================
function abrirModal(idMercado) {
    mercadoSelecionado = idMercado;
    notaSelecionada = 0;

    limparEstrelas();

    document.getElementById("modal-bg").style.display = "flex";
}

function fecharModal() {
    document.getElementById("modal-bg").style.display = "none";
}

// ==========================================
// CONFIGURAR ESTRELAS (hover + click)
// ==========================================
function configurarEstrelas() {
    const estrelas = document.querySelectorAll(".estrela");

    estrelas.forEach(star => {
        const valor = parseInt(star.dataset.star);

        // Hover temporário
        star.addEventListener("mouseover", () => {
            pintarEstrelas(valor);
        });

        // Saiu do hover
        star.addEventListener("mouseout", () => {
            pintarEstrelas(notaSelecionada);
        });

        // Clique fixa a nota
        star.addEventListener("click", () => {
            notaSelecionada = valor;
            pintarEstrelas(valor);
        });
    });
}

function pintarEstrelas(qtd) {
    document.querySelectorAll(".estrela").forEach(star => {
        const valor = parseInt(star.dataset.star);

        star.classList.toggle("selecionada", valor <= qtd);
    });
}

function limparEstrelas() {
    document.querySelectorAll(".estrela").forEach(s => {
        s.classList.remove("selecionada");
    });
}

// ==========================================
// ENVIAR AVALIAÇÃO
// ==========================================
function configurarBotaoEnviar() {
    document.getElementById("btnEnviarAvaliacao")
        .addEventListener("click", async () => {

        if (notaSelecionada === 0) {
            alert("Selecione uma nota.");
            return;
        }

        const avaliacao = {
            idMercado: mercadoSelecionado,
            nota: notaSelecionada
        };

        await fetch("http://localhost:8080/api/avaliacoes", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(avaliacao)
        });

        fecharModal();
        carregarMercados();
    });
}
