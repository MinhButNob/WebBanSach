export const users = [
  {
    id: 1,
    name: 'Nguyen Van A',
    email: 'student@school.edu.vn',
    role: 'Student',
    membershipStatus: 'Hoat dong',
    currentLoans: 2,
    penaltyBalance: 0,
    phone: '0901001001',
    address: 'Hanoi, Vietnam',
    cardNumber: 'LMS-1001',
    validFrom: '2024-01-01',
    validTo: '2026-12-31',
    maxLoans: 5,
  },
  {
    id: 2,
    name: 'Tran Thi B',
    email: 'librarian@school.edu.vn',
    role: 'Librarian',
    membershipStatus: 'Hoat dong',
    currentLoans: 0,
    penaltyBalance: 0,
    phone: '0902002002',
    address: 'Library office',
    cardNumber: 'LIB-2001',
    validFrom: '2023-06-01',
    validTo: '2026-12-31',
    maxLoans: 10,
  },
  {
    id: 3,
    name: 'Admin User',
    email: 'admin@school.edu.vn',
    role: 'Admin',
    membershipStatus: 'Hoat dong',
    currentLoans: 0,
    penaltyBalance: 0,
    phone: '0903003003',
    address: 'System admin',
    cardNumber: 'ADM-3001',
    validFrom: '2023-01-01',
    validTo: '2026-12-31',
    maxLoans: 20,
  },
  {
    id: 4,
    name: 'Le Minh Duc',
    email: 'duc@school.edu.vn',
    role: 'Student',
    membershipStatus: 'Tam khoa',
    currentLoans: 1,
    penaltyBalance: 50000,
    phone: '0904004004',
    address: 'Da Nang',
    cardNumber: 'LMS-1002',
    validFrom: '2024-02-01',
    validTo: '2026-12-31',
    maxLoans: 5,
  },
  {
    id: 5,
    name: 'Pham Thu Ha',
    email: 'ha@school.edu.vn',
    role: 'Student',
    membershipStatus: 'Hoat dong',
    currentLoans: 3,
    penaltyBalance: 25000,
    phone: '0905005005',
    address: 'Ho Chi Minh City',
    cardNumber: 'LMS-1003',
    validFrom: '2024-03-01',
    validTo: '2026-12-31',
    maxLoans: 5,
  },
]

export const demoAccounts = users.slice(0, 3)

export const categories = [
  'Tat ca the loai',
  'Lap trinh',
  'Thiet ke phan mem',
  'Co so du lieu',
  'DevOps',
  'UI/UX',
  'Tham khao',
]

export const books = [
  {
    id: 1,
    title: 'Clean Code',
    isbn: '9780132350884',
    authors: ['Robert C. Martin'],
    categories: ['Lap trinh'],
    totalCopies: 5,
    availableCopies: 2,
    publisher: 'Prentice Hall',
    language: 'English',
    edition: '1st',
    publishedYear: 2008,
    borrowedCount: 98,
    description: 'A practical guide to writing readable, maintainable, and professional code.',
  },
  {
    id: 2,
    title: 'Design Patterns',
    isbn: '9780201633610',
    authors: ['Erich Gamma', 'Richard Helm', 'Ralph Johnson', 'John Vlissides'],
    categories: ['Lap trinh', 'Thiet ke phan mem'],
    totalCopies: 3,
    availableCopies: 0,
    publisher: 'Addison-Wesley',
    language: 'English',
    edition: '1st',
    publishedYear: 1994,
    borrowedCount: 94,
    description: 'The classic catalog of reusable object-oriented software design patterns.',
  },
  {
    id: 3,
    title: 'Refactoring',
    isbn: '9780134757599',
    authors: ['Martin Fowler'],
    categories: ['Lap trinh', 'Thiet ke phan mem'],
    totalCopies: 4,
    availableCopies: 1,
    publisher: 'Addison-Wesley',
    language: 'English',
    edition: '2nd',
    publishedYear: 2018,
    borrowedCount: 88,
    description: 'Improving the design of existing code without changing its external behavior.',
  },
  {
    id: 4,
    title: 'The Pragmatic Programmer',
    isbn: '9780135957059',
    authors: ['Andrew Hunt', 'David Thomas'],
    categories: ['Lap trinh', 'Tham khao'],
    totalCopies: 5,
    availableCopies: 3,
    publisher: 'Addison-Wesley',
    language: 'English',
    edition: '20th Anniversary',
    publishedYear: 2019,
    borrowedCount: 90,
    description: 'A guide to practical software craftsmanship and everyday engineering habits.',
  },
  {
    id: 5,
    title: 'Head First Design Patterns',
    isbn: '9781492078005',
    authors: ['Eric Freeman', 'Elisabeth Robson'],
    categories: ['Lap trinh', 'Thiet ke phan mem'],
    totalCopies: 6,
    availableCopies: 2,
    publisher: "O'Reilly Media",
    language: 'English',
    edition: '2nd',
    publishedYear: 2021,
    borrowedCount: 84,
    description: 'An engaging introduction to the most common object-oriented design patterns.',
  },
  {
    id: 6,
    title: 'Vue.js Up and Running',
    isbn: '9781491997681',
    authors: ['Callum Macrae'],
    categories: ['Lap trinh', 'UI/UX'],
    totalCopies: 4,
    availableCopies: 4,
    publisher: "O'Reilly Media",
    language: 'English',
    edition: '1st',
    publishedYear: 2023,
    borrowedCount: 76,
    description: 'Build modern frontend applications with Vue 3 and a component-first workflow.',
  },
]

export const dashboardStats = [
  { label: 'Sach borrowed', value: 4, icon: 'fa-book-open', tone: 'primary' },
  { label: 'Sach qua han', value: 1, icon: 'fa-triangle-exclamation', tone: 'danger' },
  { label: 'Tong tien phat', value: '50,000 VND', icon: 'fa-coins', tone: 'warning' },
  { label: 'Dat truoc dang hoat dong', value: 2, icon: 'fa-bookmark', tone: 'success' },
]

export const currentLoans = [
  {
    id: 1,
    bookTitle: 'Clean Code',
    loanDate: '2024-01-01',
    dueDate: '2024-01-15',
    status: 'Muoned',
    daysRemaining: 3,
  },
  {
    id: 2,
    bookTitle: 'Refactoring',
    loanDate: '2024-01-04',
    dueDate: '2024-01-18',
    status: 'Qua han',
    daysRemaining: -2,
  },
  {
    id: 3,
    bookTitle: 'Vue.js Up and Running',
    loanDate: '2024-01-10',
    dueDate: '2024-01-24',
    status: 'Muoned',
    daysRemaining: 8,
  },
]

export const loanHistory = [
  {
    id: 1,
    bookTitle: 'Design Patterns',
    loanDate: '2023-12-01',
    returnDate: '2023-12-18',
    status: 'Traed',
    fineAmount: 50000,
  },
  {
    id: 2,
    bookTitle: 'The Pragmatic Programmer',
    loanDate: '2023-11-10',
    returnDate: '2023-11-24',
    status: 'Traed',
    fineAmount: 0,
  },
  {
    id: 3,
    bookTitle: 'Clean Architecture',
    loanDate: '2023-10-02',
    returnDate: '2023-10-20',
    status: 'Traed',
    fineAmount: 0,
  },
]

export const fineList = [
  {
    id: 1,
    bookTitle: 'Design Patterns',
    reason: 'Qua han',
    amount: 50000,
    issuedDate: '2024-01-16',
    dueDate: '2024-02-15',
    status: 'Chua thanh toan',
  },
  {
    id: 2,
    bookTitle: 'Refactoring',
    reason: 'Late return',
    amount: 25000,
    issuedDate: '2024-01-20',
    dueDate: '2024-02-10',
    status: 'Da thanh toan',
  },
  {
    id: 3,
    bookTitle: 'Clean Code',
    reason: 'Qua han',
    amount: 0,
    issuedDate: '2024-01-25',
    dueDate: '2024-02-05',
    status: 'Da thanh toan',
  },
]

export const topBorrowedBooks = [
  { title: 'Clean Code', count: 98 },
  { title: 'Design Patterns', count: 94 },
  { title: 'The Pragmatic Programmer', count: 90 },
  { title: 'Refactoring', count: 88 },
  { title: 'Head First Design Patterns', count: 84 },
]

export const recentActivity = [
  {
    id: 1,
    icon: 'fa-user-check',
    title: 'Nguyen Van A borrowed Clean Code',
    time: '10 minutes ago',
  },
  {
    id: 2,
    icon: 'fa-rotate-left',
    title: 'Design Patterns was returned with a fine',
    time: '1 hour ago',
  },
  {
    id: 3,
    icon: 'fa-bookmark',
    title: 'A reservation was created for Refactoring',
    time: '3 hours ago',
  },
  { id: 4, icon: 'fa-coins', title: 'Penalty balance updated for Pham Thu Ha', time: 'Yesterday' },
]

export const returningUsers = [
  {
    id: 1,
    name: 'Nguyen Van A',
    email: 'student@school.edu.vn',
    cardNumber: 'LMS-1001',
    role: 'Student',
    penaltyBalance: 50000,
    loans: [
      {
        id: 1,
        bookTitle: 'Clean Code',
        dueDate: '2024-01-15',
        status: 'Qua han',
        fineAmount: 0,
        selected: true,
      },
      {
        id: 2,
        bookTitle: 'Refactoring',
        dueDate: '2024-01-18',
        status: 'Muoned',
        fineAmount: 0,
        selected: false,
      },
    ],
  },
  {
    id: 2,
    name: 'Le Minh Duc',
    email: 'duc@school.edu.vn',
    cardNumber: 'LMS-1002',
    role: 'Student',
    penaltyBalance: 50000,
    loans: [
      {
        id: 3,
        bookTitle: 'The Pragmatic Programmer',
        dueDate: '2024-01-12',
        status: 'Qua han',
        fineAmount: 50000,
        selected: false,
      },
    ],
  },
]

export const reportResults = {
  'Sach duoc muon nhieu nhat': [
    { label: 'Clean Code', value: 98 },
    { label: 'Design Patterns', value: 94 },
    { label: 'The Pragmatic Programmer', value: 90 },
  ],
  'Nguoi dung hoat dong nhieu nhat': [
    { label: 'Nguyen Van A', value: 18 },
    { label: 'Pham Thu Ha', value: 15 },
    { label: 'Le Minh Duc', value: 11 },
  ],
  'Qua han statistics': [
    { label: 'Current overdue loans', value: 7 },
    { label: 'Nguoi dung with fines', value: 4 },
    { label: 'Average delay (days)', value: 5 },
  ],
  'Doanh thu tu tien phat': [
    { label: 'January 2024', value: '125,000 VND' },
    { label: 'February 2024', value: '180,000 VND' },
    { label: 'March 2024', value: '95,000 VND' },
  ],
}

export const getBookById = (id) => books.find((book) => book.id === Number(id)) ?? books[0]

export const getUserByEmailOrCard = (query) =>
  returningUsers.find(
    (user) =>
      user.email.toLowerCase().includes(query.toLowerCase()) ||
      user.cardNumber.toLowerCase().includes(query.toLowerCase()),
  ) ?? returningUsers[0]




