const select = document.getElementById('theme-select');
const themeKey = 'iti-theme';

const applyTheme = (theme) => {
    document.body.classList.remove('theme-iti', 'theme-emerald', 'theme-slate');
    document.body.classList.add(`theme-${theme}`);
};

const savedTheme = localStorage.getItem(themeKey) || 'iti';
applyTheme(savedTheme);

if (select) {
    select.value = savedTheme;
    select.addEventListener('change', (event) => {
        const theme = event.target.value;
        localStorage.setItem(themeKey, theme);
        applyTheme(theme);
    });
}
