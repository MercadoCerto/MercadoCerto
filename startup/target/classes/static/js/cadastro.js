async function registerUser(e) {
  e.preventDefault();

  const nome = document.getElementById('nome').value;
  const email = document.getElementById('email').value;
  const senha = document.getElementById('senha').value;

  if (!nome || !email || !senha) {
    toast('Preencha todos os campos');
    return;
  }

  if (!isValidEmail(email)) {
    toast('Email inválido');
    return;
  }

  if (senha.length < 6) {
    toast('A senha deve ter pelo menos 6 caracteres');
    return;
  }

  const body = {
    nomeUsuario: nome,
    email: email,
    login: email,
    senha: senha
  };

  const res = await fetch('http://localhost:8080/api/usuarios/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  });

  if (res.ok) {
    toast('Cadastro realizado com sucesso!');
    window.location = 'login.html';
  } else {
    const txt = await res.text();
    toast('Erro ao cadastrar usuário: ' + txt);
  }
}

document.addEventListener('DOMContentLoaded', () => {
  const f = document.getElementById('form-cadastro');
  if (f) f.addEventListener('submit', registerUser);
});
