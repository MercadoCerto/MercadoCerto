async function doLogin(e){ e.preventDefault();
 const login=document.getElementById('login').value; const senha=document.getElementById('password').value;
 if(!login||!senha){ toast('Preencha login e senha'); return; }
 const res=await fetch('/api/usuarios/login',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({login:login,senha:senha})});
 if(res.ok){ toast('Login ok'); window.location='index.html'; } else toast('Credenciais invÃ¡lidas');
}
document.addEventListener('DOMContentLoaded', ()=>{ const f=document.getElementById('form-login'); if(f) f.addEventListener('submit', doLogin); });
