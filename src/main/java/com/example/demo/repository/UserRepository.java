package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Tránh lỗi NullPointerException bằng cách dùng Optional
    User findByEmail(String email); 
}

/*
 * “Tại sao dùng Optional<User> mà không trả về User luôn cho nhanh?”

🧩 1️⃣ Tình huống thực tế

Giả sử bạn có repository:

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}


và bạn gọi nó:

User user = userRepository.findByEmail("abc@gmail.com");


➡️ Nhưng... nếu trong database không có user này thì sao?

❌ Spring sẽ không có gì để trả về cả → user = null

Và nếu bạn quên kiểm tra mà dùng user.getName()
→ Nổ lỗi NullPointerException ⚡
(“vụ nổ huyền thoại” trong Java 😆)

🧠 2️⃣ Optional sinh ra để chống null

Optional là một “hộp” có thể có hoặc không có giá trị.

Ví dụ:

Optional<User> result = userRepository.findByEmail("abc@gmail.com");


Nếu có user trong DB → hộp chứa user.

Nếu không có → hộp rỗng (Optional.empty()).

🪄 3️⃣ Cách dùng an toàn
Optional<User> result = userRepository.findByEmail("abc@gmail.com");

if (result.isPresent()) {
    User user = result.get(); // lấy ra an toàn
    System.out.println(user.getName());
} else {
    System.out.println("Không tìm thấy user!");
}


→ Không còn lo NullPointerException.

⚙️ 4️⃣ Các cách tiện khác của Optional
Cách	Ý nghĩa
.isPresent()	Kiểm tra có giá trị không
.get()	Lấy giá trị bên trong
.orElse(x)	Nếu rỗng thì trả về giá trị mặc định
.orElseThrow()	Nếu rỗng thì ném lỗi
.ifPresent(u -> ...)	Thực thi code nếu có giá trị

Ví dụ:

User user = userRepository.findByEmail("abc@gmail.com")
               .orElse(null); // nếu không có -> null


Hoặc:

userRepository.findByEmail("abc@gmail.com")
    .ifPresent(u -> System.out.println(u.getName()));

✅ 5️⃣ Tóm lại cực ngắn
Câu hỏi	Trả lời
Optional là gì?	Một “hộp” có thể chứa hoặc không chứa giá trị
Dùng để làm gì?	Tránh lỗi NullPointerException
Vì sao Spring dùng nó?	Vì kết quả findBy... có thể không tồn tại
Có bắt buộc không?	Không, nhưng dùng Optional giúp code an toàn hơn nhiều ✅
🧩 Ví dụ dễ hiểu ngoài đời:

Optional giống như hộp quà — có thể có quà, có thể rỗng.

Trước khi mở (dùng), bạn phải kiểm tra:
“Trong hộp này có gì không?” 🎁

Nếu có → lấy ra dùng.
Nếu không → khỏi mở, khỏi bị “sốc điện” (NullPointerException 😂)
 */