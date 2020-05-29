SET
@id = 1,

-- Student loan data
@sl_plan_one_threshold = 18935,
@sl_plan_one_rate = 9,
@sl_plan_two_threshold = 25725,
@sl_plan_two_rate = 9,

-- Tax data
@personal_allowance = 12500,
@basic_threshold = 37500,
@basic_rate = 20,
@higher_threshold = 150000,
@higher_rate = 40,
@additional_rate = 45,

-- National Insurance Class 2 data
@class_two = 3,
@class_two_threshold = 6365,
-- National Insurance Class 4 data
@lower_profits_limit = 8632,
@lower_profits_limit_rate = 9,
@upper_profits_limit = 50000,
@upper_profits_limit_rate = 2,
-- Tax year start date
@tax_year_starts = '2019-04-6';

INSERT INTO role (id, name) VALUES (@id, @role) ON DUPLICATE KEY UPDATE name=@role;

INSERT INTO user_roles (user_id, role_id) VALUES (@id, @id) ON DUPLICATE KEY UPDATE user_id=@id, role_id=@id;

INSERT INTO student_loan(id, tax_year_starts, plan_one_annual_threshold, plan_one_rate, plan_two_annual_threshold, plan_two_rate)
VALUES
(
    @id,
    @tax_year_starts,
    @sl_plan_one_threshold,
    @sl_plan_one_rate,
    @sl_plan_two_threshold,
    @sl_plan_two_rate
)
ON DUPLICATE KEY UPDATE
    tax_year_starts = @tax_year_starts,
    plan_one_annual_threshold = @sl_plan_one_threshold,
    plan_one_rate = @sl_plan_one_rate,
    plan_two_annual_threshold = @sl_plan_two_threshold,
    plan_two_rate = @sl_plan_two_rate;



INSERT INTO tax (id, tax_year_starts, personal_allowance, basic_threshold, basic_rate, higher_threshold, higher_rate, additional_rate)
VALUES
(
    @id,
    @tax_year_starts,
    @personal_allowance,
    @basic_threshold,
    @basic_rate,
    @higher_threshold,
    @higher_rate,
    @additional_rate
)

ON DUPLICATE KEY UPDATE
    tax_year_starts =@tax_year_starts,
    personal_allowance = @personal_allowance,
    basic_threshold = @basic_threshold,
    basic_rate = @basic_rate,
    higher_threshold = @higher_threshold,
    higher_rate = @higher_rate,
    additional_rate = @additional_rate;




INSERT INTO national_insurance
(
    id,
    tax_year_starts,
    class_two,
    class_two_threshold,
    lower_profits_limit,
    lower_profits_limit_rate,
    upper_profits_limit,
    upper_profits_limit_rate
)
VALUES
(
    @id,
    @tax_year_starts,
    @class_two,
    @class_two_threshold,
    @lower_profits_limit,
    @lower_profits_limit_rate,
    @upper_profits_limit,
    @upper_profits_limit_rate
)

ON DUPLICATE KEY UPDATE
    tax_year_starts = @tax_year_starts,
    class_two = @class_two,
    class_two_threshold = @class_two_threshold,
    lower_profits_limit = @lower_profits_limit,
    lower_profits_limit_rate = @lower_profits_limit_rate,
    upper_profits_limit = @upper_profits_limit,
    upper_profits_limit_rate = @upper_profits_limit_rate;


INSERT INTO tax_year (id, tax_year_starts, tax_id, national_insurance_id, student_loan_id)
VALUES
(@id, @tax_year_starts, @id, @id, @id)
ON DUPLICATE KEY UPDATE
    tax_year_starts = @tax_year_starts,
    tax_id = @id,
    national_insurance_id = @id,
    student_loan_id = @id;
