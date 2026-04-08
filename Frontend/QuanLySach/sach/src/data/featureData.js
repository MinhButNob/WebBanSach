import { books, users, fineList } from './mockData'

export const reservationStatuses = [
  'PENDING',
  'READY_FOR_PICKUP',
  'EXPIRED',
  'CANCELLED',
  'COMPLETED',
]
export const copyStatuses = ['AVAILABLE', 'BORROWED', 'LOST']
export const paymentMethods = ['CASH', 'CARD', 'TRANSFER']
export const paymentStatuses = ['PENDING', 'CONFIRMED', 'FAILED']

export const reservationsSeed = [
  {
    id: 1,
    userId: 1,
    userName: 'Nguyen Van A',
    bookId: 2,
    bookTitle: 'Design Patterns',
    reservationDate: '2026-04-01',
    expiryDate: '2026-04-05',
    status: 'PENDING',
  },
  {
    id: 2,
    userId: 5,
    userName: 'Pham Thu Ha',
    bookId: 4,
    bookTitle: 'Clean Architecture',
    reservationDate: '2026-04-02',
    expiryDate: '2026-04-06',
    status: 'READY_FOR_PICKUP',
  },
]

export const bookCopiesSeed = books.flatMap((book) => {
  return Array.from({ length: Math.max(1, book.totalCopies) }, (_, index) => ({
    id: Number(`${book.id}${String(index + 1).padStart(2, '0')}`),
    bookId: book.id,
    bookTitle: book.title,
    barcode: `BC-${book.id}-${String(index + 1).padStart(3, '0')}`,
    status: index < book.availableCopies ? 'AVAILABLE' : 'BORROWED',
    shelf: `S-${((book.id - 1) % 5) + 1}`,
  }))
})

export const catalogsSeed = {
  authors: Array.from(
    new Set(books.flatMap((book) => book.authors).map((name, index) => `${index + 1}|${name}`)),
  ).map((pair) => {
    const [id, name] = pair.split('|')
    return { id: Number(id), name }
  }),
  categories: Array.from(new Set(books.flatMap((book) => book.categories))).map((name, index) => ({
    id: index + 1,
    name,
  })),
  publishers: Array.from(new Set(books.map((book) => book.publisher))).map((name, index) => ({
    id: index + 1,
    name,
  })),
  shelves: Array.from({ length: 8 }, (_, index) => ({
    id: index + 1,
    code: `S-${index + 1}`,
    floor: index < 4 ? 'F1' : 'F2',
  })),
}

export const paymentsSeed = fineList.map((fine, index) => ({
  id: index + 1,
  fineId: fine.id,
  userName: users[(index + 1) % users.length].name,
  amount: fine.amount,
  paymentDate: `2026-04-0${(index % 3) + 1}`,
  method: paymentMethods[index % paymentMethods.length],
  status: fine.status === 'Da thanh toan' ? 'CONFIRMED' : 'PENDING',
}))
