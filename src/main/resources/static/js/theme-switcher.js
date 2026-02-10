const themeKey = 'iti-theme';

const applyTheme = (theme) => {
    if (!document.body) {
        return;
    }
    document.body.classList.remove('theme-iti', 'theme-emerald', 'theme-slate');
    document.body.classList.add(`theme-${theme}`);
    document.documentElement.setAttribute('data-theme', theme);
};

const initThemeSwitcher = () => {
    const select = document.getElementById('theme-select');
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
};

if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initThemeSwitcher);
} else {
    initThemeSwitcher();
}
