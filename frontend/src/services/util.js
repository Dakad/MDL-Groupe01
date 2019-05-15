import ColorHash from 'color-hash';

const colorHash = new ColorHash();

export function getColorHashOf(str) {
  return colorHash.hex(str);
}
