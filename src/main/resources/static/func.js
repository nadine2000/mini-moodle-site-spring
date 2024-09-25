function toggle1(id1, id2) {
    document.getElementById(id1).classList.remove('d-none');
    document.getElementById(id2).classList.add('d-none');
}

function toggle(id1, id2) {
    const button1 = document.getElementById(id1);
    const button2 = document.getElementById(id2);
    if (button1.classList.contains('d-none')) {
        button1.classList.remove('d-none');
        button2.classList.add('d-none');
    } else {
        button1.classList.add('d-none');
        button2.classList.remove('d-none');
    }
}
